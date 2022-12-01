package com.ada.gameshop.controller;

import com.ada.gameshop.dto.CustomerDTO;
import com.ada.gameshop.exception.UserNotFoundException;
import com.ada.gameshop.model.Customer;
import com.ada.gameshop.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping(value = "customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;


    @PostMapping("/save")
    public final ResponseEntity<?> addUser(@RequestBody final CustomerDTO newCustomer) {
        try {
            return new ResponseEntity<>(customerService.addNewCustomer(newCustomer), HttpStatus.CREATED);
        } catch (IllegalStateException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/save2")
    public ResponseEntity create(@PathVariable(value = "customer-id") Long customerId,
                                 @RequestBody CustomerDTO personDTO) {
       CustomerDTO createdPersonDTO = customerService.create(personDTO);

        return new ResponseEntity(personDTO.getCustomerId(), HttpStatus.CREATED);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity retrieveById(@PathVariable Long customerId) {
        CustomerDTO customerDTO = customerService.retrieveById(customerId);

        return new ResponseEntity(customerDTO, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllCustomers() {
        try {
            List<Customer> findAll = customerService.getCustomers();
            return new ResponseEntity<>(findAll, HttpStatus.OK);
        } catch (UserNotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update")
    public final ResponseEntity<?> editUser(@RequestBody final CustomerDTO customerDTO) {
        try {
            return new ResponseEntity<>(customerService.editCustomer(customerDTO), HttpStatus.OK);
        } catch (UserNotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalStateException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);

        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try{
            customerService.deleteCustomer(id);
            return new ResponseEntity<>("Customer successfully deleted", HttpStatus.OK);
        }
        catch (NoSuchElementException | UserNotFoundException ex) {
            throw new UserNotFoundException();
        }
    }

    @PatchMapping("/{customerId}")
    public ResponseEntity modify(@PathVariable Long customerId,
                                 @RequestBody Map<String, Object> fieldsToModify) {
        customerService.modify(customerId, fieldsToModify);

        return new ResponseEntity(HttpStatus.OK);
    }

}
