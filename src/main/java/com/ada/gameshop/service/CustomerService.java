package com.ada.gameshop.service;

import com.ada.gameshop.dto.CustomerDTO;
import com.ada.gameshop.exception.ResourceNotFoundException;
import com.ada.gameshop.exception.UserNotFoundException;
import com.ada.gameshop.model.Customer;
import com.ada.gameshop.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityExistsException;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    private TransactionService transactionService;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {

        this.customerRepository = customerRepository;
    }

    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    public Customer addNewCustomer(final CustomerDTO newCustomer) {
        if (!customerRepository.existsById(newCustomer.getCustomerId())) {
            return customerRepository.save(
                    Customer.builder()
                            .id(newCustomer.getCustomerId())
                            .name(newCustomer.getName())
                            .lastName(newCustomer.getLastName())
                            .email(newCustomer.getEmail())
                            .telephone(newCustomer.getTelephone())
                            .build());
        } else {
            log.warn("Customer already registered");
            throw new IllegalStateException(newCustomer.getName() + " is already registered");
        }
    }

    public Customer editCustomer(final CustomerDTO customer) {
        Optional<Customer> optionalCustomer = customerRepository.findById(customer.getCustomerId());
        if(optionalCustomer.isPresent()) {
            optionalCustomer.get().setName(customer.getName());
            optionalCustomer.get().setLastName(customer.getLastName());
            optionalCustomer.get().setEmail(customer.getEmail());
            optionalCustomer.get().setTelephone(customer.getTelephone());
            return optionalCustomer.get();
        }
        else throw new UserNotFoundException();
    }

    public void modify(Long customerId, Map<String, Object> fieldsToModify) {
        Optional<Customer> person = customerRepository.findById(customerId);
        if (person.isEmpty()) {
            throw new ResourceNotFoundException();
        }
        Customer customerToModify = person.get();
        fieldsToModify.forEach((key, value) -> customerToModify.modifyAttributeValue(key, value));
        customerRepository.save(customerToModify);
    }

    public CustomerDTO create(CustomerDTO personDTO) {
        Customer customer = mapToEntity(personDTO);
        checkForExistingCustomer(customer.getId());
        customer = customerRepository.save(customer);
        if (!CollectionUtils.isEmpty(personDTO.getTransactionDTOS())) {
            transactionService.create(personDTO.getTransactionDTOS(), customer);
        }

        return personDTO;
    }

    public CustomerDTO retrieveById(Long id) {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isEmpty()) {
            throw new ResourceNotFoundException();
        }

        return mapToDTO(customer.get());
    }

    private void checkForExistingCustomer(Long customerId) {
        if (customerRepository.existsById(customerId)) {
            throw new EntityExistsException();
        }
    }

    private Customer mapToEntity(CustomerDTO customerDTO) {
        Customer person = new Customer(customerDTO.getCustomerId(), customerDTO.getName(),
                customerDTO.getLastName(), customerDTO.getEmail(), customerDTO.getTelephone());

        return person;
    }

    private CustomerDTO mapToDTO(Customer customer) {
        CustomerDTO personDTO = new CustomerDTO(customer.getId(), customer.getName(),
                customer.getLastName(), customer.getEmail(), customer.getTelephone(), transactionService.mapToDTOS(customer.getTransactions()));

        return personDTO;
    }

    public void deleteCustomer(final Long id) {
        Optional<Customer> customerOptional = customerRepository.findById(id);
        if(customerOptional.isPresent()){
            customerRepository.deleteById(id);
        }
        else throw new NoSuchElementException();
    }

}
