package com.ada.gameshop.service;


import com.ada.gameshop.exception.ResourceNotFoundException;
import com.ada.gameshop.model.Customer;
import com.ada.gameshop.model.Transaction;
import com.ada.gameshop.repository.CustomerRepository;
import com.ada.gameshop.repository.TransactionRepository;
import com.ada.gameshop.utils.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransactionService {

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
//        Customer user = util.getCustomerById(newTransaction.getCustomerId());
//
//        if(!transactionRepository.existsById(newTransaction.getTransactionId())){
//            return transactionRepository.save(
//                    Transaction.builder()
//                            .id(newTransaction.getTransactionId())
//                            .date(newTransaction.getDate())
//                            .build());
//
//            user
//        }
//        else {
//            throw new IllegalStateException("Transaction already created");
//        }
//    }

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
