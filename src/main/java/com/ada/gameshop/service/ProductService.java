package com.ada.gameshop.service;

import com.ada.gameshop.dto.ProductDTO;
import com.ada.gameshop.exception.ResourceNotFoundException;
import com.ada.gameshop.model.Product;
import com.ada.gameshop.model.Transaction;
import com.ada.gameshop.repository.ProductRepository;
import com.ada.gameshop.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private ProductRepository productRepository;

    private TransactionRepository transactionRepository;

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public void create(ProductDTO productDTO, Long transactionId) {
        Optional<Transaction> transaction = transactionRepository.findById(transactionId);
        if (transaction.isEmpty()) {
            throw new ResourceNotFoundException();
        }

        Product product = mapToEntity(productDTO);
        product = productRepository.save(product);
        productDTO.setId(product.getId());
    }

    public void create(List<ProductDTO> productDTOS) {
        List<Product> products = productDTOS.stream()
                .map(productDTO -> mapToEntity(productDTO))
                .collect(Collectors.toList());
        productRepository.saveAll(products);
    }
    public ProductDTO retrieveById(Long transactionId) {
        if (!transactionRepository.existsById(transactionId)){
            throw new ResourceNotFoundException();
        }
        Optional<Product> product = productRepository.findById(transactionId);
        if (product.isEmpty()) {
            throw new ResourceNotFoundException();
        }
        return mapToDTO(product.get());
    }
    private Product mapToEntity(ProductDTO productDTO) {
        Product product = new Product(productDTO.getName(), productDTO.getPrice(),
            productDTO.getDeveloper(), LocalDate.parse(productDTO.getReleaseDate(), DATE_TIME_FORMATTER),
                productDTO.getCategory(), productDTO.getPegi(), productDTO.getPlatform(), productDTO.getType(),
                productDTO.getGeneration());
        return product;
    }
    public List<ProductDTO> mapToDTOS(List<Product> products) {
        return products.stream()
                .map(product -> mapToDTO(product))
                .collect(Collectors.toList());
    }

    private ProductDTO mapToDTO(Product product) {
        ProductDTO productDTO = new ProductDTO(product.getName(), product.getPrice(), product.getDeveloper(),
                product.getReleaseDate().toString(), product.getCategory(), product.getPegi(), product.getPlatform(),
                product.getType(), product.getGeneration());
        productDTO.setId(product.getId());

        return productDTO;
    }

    public void delete(Long productId) {
        try {
            productRepository.deleteById(productId);
        } catch (EmptyResultDataAccessException e){
            throw new ResourceNotFoundException();
        }
    }

}
