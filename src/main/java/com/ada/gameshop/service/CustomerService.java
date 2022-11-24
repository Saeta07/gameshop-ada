package com.ada.gameshop.service;

import com.ada.gameshop.dto.CustomerDTO;
import com.ada.gameshop.model.Customer;
import com.ada.gameshop.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
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
            log.warn("Employee already registered");
            throw new IllegalStateException(newCustomer.getName() + " is already registered");
        }
    }

}
