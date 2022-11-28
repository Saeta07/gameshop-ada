package com.ada.gameshop.service;


import com.ada.gameshop.dto.NewTransactionDTO;
import com.ada.gameshop.exception.ResourceNotFoundException;
import com.ada.gameshop.model.Customer;
import com.ada.gameshop.model.Product;
import com.ada.gameshop.model.Transaction;
import com.ada.gameshop.repository.CustomerRepository;
import com.ada.gameshop.repository.TransactionRepository;
import com.ada.gameshop.utils.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private List<Product> products;
    private Customer customer;

    @Autowired
    private TransactionRepository transactionRepository;

    private CustomerRepository customerRepository;

    private Util util;

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public Transaction addTransaction(Transaction transaction) {
        Transaction savedTransaction = transactionRepository.save(transaction);
        return savedTransaction;
    }

    public List<Transaction> getTransactions() {
        return transactionRepository.findAll();
    }

//    @Transactional
//    public Transaction addTransaction (final NewTransactionDTO newTransaction) {
//
//        else {
//            throw new IllegalStateException("Transaction already created");
//        }
//    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public Transaction save(Long customerId) {

        Transaction pedido = new NewTransactionDTO();
        pedido(customerId);
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String fecha = date.format(formatter);
        pedido.setDate(fecha);
        return transactionRepository.save(pedido);
    }




    public void delete(Long transactionId) {
        try {
            transactionRepository.deleteById(transactionId);
        } catch (EmptyResultDataAccessException e){
            throw new ResourceNotFoundException();
        }
    }

    public void deleteTransaction(final Long transactionId) {

        Optional<Transaction> transactionOptional = transactionRepository.findById(transactionId);
        if (transactionOptional.isPresent()) {
            Customer user = util.getCustomerByTransaction(transactionOptional.get());
            user.removeTransaction(transactionOptional.get());
            transactionRepository.deleteById(transactionId);
        } else {
            throw new NoSuchElementException("Transaction with ID: " + transactionId + " does not exist.");
        }

    }

}
