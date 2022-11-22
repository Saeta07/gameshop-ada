package com.ada.gameshop.service;

import com.ada.gameshop.dto.CustomerDTO;
import com.ada.gameshop.exception.UserNotFoundException;
import com.ada.gameshop.model.Customer;
import com.ada.gameshop.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerService {

    private CustomerRepository customerRepository;

    public Customer addNewCustomer(final CustomerDTO newCustomer) {
        if (!customerRepository.existsById(newCustomer.getCustomerId())) {
            return customerRepository.save(
                    Customer.builder()
                            .id(newCustomer.getCustomerId())
                            .name(newCustomer.getName())
                            .lastName(newCustomer.getLastName())
                            .email(newCustomer.getEmail())
                            .telephone(newCustomer.getTelephone())
                            .orderUSer(newCustomer.getOrderUser())
                            .orders(new ArrayList<>())
                            .build());
        } else {
            log.warn("Employee already registered");
            throw new IllegalStateException(newCustomer.getName() + " is already registered");
        }
    }

    private void checkForExistingPerson(Long id) {
        if (customerRepository.existsById(id)) {
            throw new UserNotFoundException();
        }
    }

    public List<Customer> retrieveAll() {
        return customerRepository.findAll();
    }

}
