package com.ada.gameshop.service;

import com.ada.gameshop.dto.TransactionDTO;
import com.ada.gameshop.exception.ResourceNotFoundException;
import com.ada.gameshop.exception.UserNotFoundException;
import com.ada.gameshop.model.Customer;
import com.ada.gameshop.model.Transaction;
import com.ada.gameshop.repository.CustomerRepository;
import com.ada.gameshop.repository.TransactionRepository;
import com.ada.gameshop.utils.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;

    private final CustomerRepository customerRepository;

    private final Util util;

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public List<Transaction> getTransactions() {
        return transactionRepository.findAll();
    }

    public void create(TransactionDTO transactionDTO) {
        Optional<Customer> customer = customerRepository.findById(transactionDTO.getCustomerId());
        if (customer.isEmpty()) {
            throw new UserNotFoundException();
        }

        Transaction transaction = mapToEntity(transactionDTO, customer.get());
        transaction = transactionRepository.save(transaction);
        transactionDTO.setId(transaction.getId());
    }

    public List<TransactionDTO> retrieveAll() {
        List<Transaction> transactions = transactionRepository.findAll();
        return transactions.stream()
                .map(transaction -> mapToDTO(transaction))
                .collect(Collectors.toList());
    }

    public TransactionDTO retrieveById(Long customerId, Long transactionId) {
        if (!customerRepository.existsById(customerId)){
            throw new ResourceNotFoundException();
        }
        Optional<Transaction> transaction = transactionRepository.findById(transactionId);
        if (transaction.isEmpty()) {
            throw new ResourceNotFoundException();
        }
        return mapToDTO(transaction.get());
    }

    private Transaction mapToEntity(TransactionDTO transactionDTO, Customer customer) {
        Transaction transaction = new Transaction(transactionDTO.getId(), LocalDate.parse(transactionDTO.getDate(),
                DATE_TIME_FORMATTER), customer);
        return transaction;
    }

    public List<TransactionDTO> mapToDTOS(List<Transaction> transactions) {
        return transactions.stream()
                .map(transaction -> mapToDTO(transaction))
                .collect(Collectors.toList());
    }

    private TransactionDTO mapToDTO(Transaction transaction) {
        TransactionDTO transactionDTO = new TransactionDTO(transaction.getId(), transaction.getDate().toString(),
                transaction.getCustomer().getId());
        return transactionDTO;
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
            throw new NoSuchElementException("Transaction with ID: " + transactionId + " doesn´t exist");
        }
    }

}
