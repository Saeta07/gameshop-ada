package com.ada.gameshop.service;

import com.ada.gameshop.dto.CustomerDTO;
import com.ada.gameshop.exception.UserNotFoundException;
import com.ada.gameshop.model.Customer;
import com.ada.gameshop.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

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

    public Customer editUser(final CustomerDTO customer) {
        Optional<Customer> optionalCustomer = customerRepository.findById(customer.getCustomerId());
        if(optionalCustomer.isPresent()) {
            optionalCustomer.get().setName(customer.getName());
            optionalCustomer.get().setLastName(customer.getLastName());
            optionalCustomer.get().setEmail(customer.getEmail());
            optionalCustomer.get().setTelephone(customer.getTelephone());
            return optionalCustomer.get();
        }
        else
        throw new UserNotFoundException();
    }

    public void deleteCustomer(final Long id) {
        Optional<Customer> customerOptional = customerRepository.findById(id);
        if(customerOptional.isPresent()){
            customerRepository.deleteById(id);
        }
        else throw new NoSuchElementException();
    }

}
