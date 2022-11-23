package com.ada.gameshop.controller;

import com.ada.gameshop.dto.CustomerDTO;
import com.ada.gameshop.model.Customer;
import com.ada.gameshop.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public final ResponseEntity<?> addUser(@RequestBody final CustomerDTO newCustomer) {
        try {
            return new ResponseEntity<>(customerService.addNewCustomer(newCustomer), HttpStatus.OK);
        } catch (IllegalStateException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity <List<Customer>> findAll() {
        return new ResponseEntity(customerService.findAll(), HttpStatus.OK);
    }

    //cambios

}
