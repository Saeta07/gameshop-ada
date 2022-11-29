package com.ada.gameshop.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class NewTransactionDTO {
    private Long customerId;
    private LocalDate date;
    private List<ProductDTO> productDTOS;

    public NewTransactionDTO (Long id, LocalDate date, List<ProductDTO> productDTOS) {
        this.customerId= id;
        this.date = date;
        this.productDTOS = productDTOS;
    }

    public List<ProductDTO> getProductDTOS() {
        return productDTOS;
    }
}
