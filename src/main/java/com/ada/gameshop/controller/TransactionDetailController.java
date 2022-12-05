package com.ada.gameshop.controller;

import com.ada.gameshop.dto.TransactionDetailDTO;
import com.ada.gameshop.service.TransactionDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "transaction_detail")
@RequiredArgsConstructor
public class TransactionDetailController {

    private final TransactionDetailService transactionDetailService;
    private final TransactionDetailDTO transactionDetailDTO;

    @PostMapping
    public ResponseEntity create(@RequestBody TransactionDetailDTO transactionDetailDTO) {

        transactionDetailService.create(transactionDetailDTO);

        return new ResponseEntity(transactionDetailDTO.getId(), HttpStatus.CREATED);

    }

    @GetMapping("/{transactionDetailId}")
    public ResponseEntity retrieveById(@PathVariable Long transactionDetailId) {
        try {
            TransactionDetailDTO transactionDetailDTO = transactionDetailService.retrieveById(transactionDetailId);

            return new ResponseEntity(transactionDetailDTO, HttpStatus.OK);
        } catch (Exception e) {

            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity retrieve() {
        return new ResponseEntity(transactionDetailService.retrieveAll(), HttpStatus.OK);
    }
}
