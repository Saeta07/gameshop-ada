package com.ada.gameshop.controller;

import com.ada.gameshop.exception.TransactionNotFoundException;
import com.ada.gameshop.model.Transaction;
import com.ada.gameshop.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "transaction")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

//    @PostMapping("/save")
//    public final ResponseEntity<?> addTransaction(@PathVariable Long id,
//                                                  @RequestBody final NewTransactionDTO newTransaction) {
//        try {
//            return new ResponseEntity<>(transactionService.addTransaction(newTransaction), HttpStatus.CREATED);
//        } catch (IllegalStateException ex) {
//            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllTransactionByCustomer() {
        try {
            List<Transaction> findAll = transactionService.getTransactions();
            return new ResponseEntity<>(findAll, HttpStatus.OK);
        } catch (TransactionNotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }



}



