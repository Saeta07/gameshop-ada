package com.ada.gameshop.utils;

import com.ada.gameshop.exception.UserNotFoundException;
import com.ada.gameshop.model.Customer;
import com.ada.gameshop.model.Transaction;
import com.ada.gameshop.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class Util {

    CustomerRepository customerRepository;

    public Customer getCustomerById(Long userId) {
        Optional<Customer> customerOptional = customerRepository.findById(userId);

        if (customerOptional.isPresent()) {
            return customerOptional.get();
        } else {
            throw new UserNotFoundException();
        }
    }

    public Customer getCustomerByTransaction(final Transaction transaction) {
        Optional<Customer> userOptional =
                customerRepository.findByTransactionsContaining(transaction);
        if (userOptional.isPresent()) {
            return userOptional.get();
        } else {
            throw new UserNotFoundException();
        }
    }
}
