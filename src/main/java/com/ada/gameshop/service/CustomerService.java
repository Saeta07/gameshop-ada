package com.ada.gameshop.service;

import com.ada.gameshop.dto.CustomerDTO;
import com.ada.gameshop.exception.ExistingResourceException;
import com.ada.gameshop.exception.ResourceNotFoundException;
import com.ada.gameshop.exception.UserNotFoundException;
import com.ada.gameshop.model.Customer;
import com.ada.gameshop.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public List<CustomerDTO> retrieveAll() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream()
                .map(customer-> mapToDTO(customer))
                .collect(Collectors.toList());
    }

    public CustomerDTO create(CustomerDTO customerDTO) {
        Customer customer = mapToEntity(customerDTO);
        checkForExistingCliente((long) Math.toIntExact(customer.getId()));
        customer = customerRepository.save(customer);

        return customerDTO;
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

    public CustomerDTO retrieveById(Long id) {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isEmpty()) {
            throw new ResourceNotFoundException();
        }

        return mapToDTO(customer.get());
    }

    private Customer mapToEntity(CustomerDTO customerDTO) {
        Customer customer = new Customer(customerDTO.getCustomerId(), customerDTO.getName(), customerDTO.getLastName(),
                customerDTO.getEmail(), customerDTO.getTelephone());
        return customer;
    }

    private CustomerDTO mapToDTO(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO(customer.getId(), customer.getName(), customer.getLastName(),
                customer.getEmail(), customer.getTelephone());
        return customerDTO;
    }

    private void checkForExistingCliente(Long customerId) {
        if (customerRepository.existsById(customerId)) {
            throw new ExistingResourceException();
        }
    }

    public void modify(Long customerId, Map<String, Object> fieldsToModify) {
        Optional<Customer> person = customerRepository.findById(customerId);
        if (person.isEmpty()) {
            throw new ResourceNotFoundException();
        }
        Customer personToModify = person.get();
        fieldsToModify.forEach((key, value) -> personToModify.modifyAttributeValue(String.valueOf(key), value));
        customerRepository.save(personToModify);
    }

    public void replace(Long customerId, CustomerDTO customerDTO) {
        Optional<Customer> customer = customerRepository.findById(customerId);
        if (customer.isEmpty()) {
            throw new ResourceNotFoundException();
        }
        Customer customerToReplace = customer.get();
        customerToReplace.setName(customerDTO.getName());
        customerToReplace.setLastName(customerDTO.getLastName());
        customerToReplace.setEmail(customerDTO.getEmail());
        customerToReplace.setTelephone(customerDTO.getTelephone());
        customerRepository.save(customerToReplace);
    }

    public void delete(Long customerId) {
        try {
            customerRepository.deleteById(customerId);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException();
        }
    }

    public void deleteCustomer(final Long id) {
        Optional<Customer> customerOptional = customerRepository.findById(id);
        if(customerOptional.isPresent()){
            customerRepository.deleteById(id);
        }
        else throw new NoSuchElementException();
    }

}
