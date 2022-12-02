package com.ada.gameshop.controller;

import com.ada.gameshop.dto.TransactionDTO;
import com.ada.gameshop.exception.TransactionNotFoundException;
import com.ada.gameshop.model.Transaction;
import com.ada.gameshop.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "transaction")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity create(@RequestBody TransactionDTO transactionDTO) {
        transactionService.create(transactionDTO);
        return new ResponseEntity(transactionDTO.getId(), HttpStatus.CREATED);
    }

    @PostMapping("/save")
    public ResponseEntity createTransaction(@RequestBody TransactionDTO transactionDTO) {
        try {
            transactionService.create(transactionDTO);
            return new ResponseEntity(transactionDTO.getId(), HttpStatus.CREATED);
        }catch (IllegalStateException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/{transactionId}")
    public ResponseEntity retrieveById(@PathVariable Long customerId,
                                       @PathVariable Long transactionId){
        TransactionDTO transactionDTO = transactionService.retrieveById(customerId,transactionId);

        return new ResponseEntity(transactionDTO, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllTransactions() {
        try {
            List<Transaction> findAll = transactionService.getTransactions();
            return new ResponseEntity<>(findAll, HttpStatus.OK);
        } catch (TransactionNotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{transactionId}")
    public ResponseEntity delete(@PathVariable Long transactionId) {
        transactionService.delete(transactionId);

        return new ResponseEntity(HttpStatus.OK);
    }

}



