package com.ada.gameshop.service;

<<<<<<< HEAD
import com.ada.gameshop.dto.ProductDTO;
import com.ada.gameshop.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    public List<ProductDTO> mapToDTOS(List<Product> products) {

        return products.stream()
                .map(product -> mapToDTO(product))
                .collect(Collectors.toList());
    }

    private ProductDTO mapToDTO(Product product) {
        ProductDTO productDTO = new ProductDTO(product.getName(),
                product.getPrice(), product.getDeveloper(), product.getReleaseDate());
        productDTO.setId(product.getId());

        return productDTO;
    }
=======
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


>>>>>>> 51d99ac3142d5528ba289408d458a40f4d705d32
}
