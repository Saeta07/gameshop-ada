package com.ada.gameshop.service;

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
}
