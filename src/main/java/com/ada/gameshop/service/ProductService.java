package com.ada.gameshop.service;

import com.ada.gameshop.model.Product;
import com.ada.gameshop.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ProductService {

    private ProductRepository productRepository;

    private Map<Product, Integer> products = new HashMap<>();

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public Page<Product> findAllProductsPageable(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public void addProduct(Product product) {
        if (products.containsKey(product)) {
            products.replace(product, products.get(product) + 1);
        } else {
            products.put(product, 1);
        }
    }

    public void removeProduct(Product product) {
        if (products.containsKey(product)) {
            if (products.get(product) > 1)
                products.replace(product, products.get(product) - 1);
            else if (products.get(product) == 1) {
                products.remove(product);
            }
        }
    }


}
