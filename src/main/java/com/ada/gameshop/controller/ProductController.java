package com.ada.gameshop.controller;

import com.ada.gameshop.dto.ProductDTO;
import com.ada.gameshop.model.Product;
import com.ada.gameshop.service.ProductService;
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
@RequestMapping(value = "product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity create(@RequestBody ProductDTO productDTO) {
        try {
            productService.create(productDTO);
            return new ResponseEntity(productDTO.getProductId(), HttpStatus.CREATED);
        }catch (IllegalStateException ex) {
            return new ResponseEntity<>(productDTO.getProductId(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllProducts() {
        try {
            List<Product> findAll = productService.getProducts();
            return new ResponseEntity<>(findAll, HttpStatus.OK);
        } catch (IllegalStateException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity delete(@PathVariable Long productId) {
        productService.delete(productId);

        return new ResponseEntity("Product successfully deleted",HttpStatus.OK);
    }
}
