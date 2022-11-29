package com.ada.gameshop.service;

import com.ada.gameshop.dto.TransactionDTO;
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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CustomerRepository customerRepository;

    private ProductService productService;

    private Util util;

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public List<Transaction> getTransactions() {
        return transactionRepository.findAll();
    }

    public void create(TransactionDTO transactionDTO, Long customerId) {
        Optional<Customer> customer = customerRepository.findById(customerId);
        if (customer.isEmpty()) {
            throw new ResourceNotFoundException();
        }

        Transaction transaction = mapToEntity(transactionDTO, customer.get());
        transaction = transactionRepository.save(transaction);
        transactionDTO.setId(transaction.getId());
    }

    public void create(List<TransactionDTO> transactionDTOS, Customer customer) {
        List<Transaction> transactions = transactionDTOS.stream()
                .map(transactionDTO -> mapToEntity(transactionDTO, customer))
                .collect(Collectors.toList());
        transactionRepository.saveAll(transactions);
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
        Transaction transaction = new Transaction(LocalDate.parse(transactionDTO.getDate(), DATE_TIME_FORMATTER), customer);
        return transaction;
    }

    public List<TransactionDTO> mapToDTOS(List<Transaction> transactions) {
        return transactions.stream()
                .map(transaction -> mapToDTO(transaction))
                .collect(Collectors.toList());
    }

    private TransactionDTO mapToDTO(Transaction transaction) {
        TransactionDTO transactionDTO = new TransactionDTO(transaction.getId(), transaction.getDate().toString(),
                productService.mapToDTOS(transaction.getProducts()));

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
            throw new NoSuchElementException("Transaction with ID: " + transactionId + " does not exist.");
        }
    }

}
