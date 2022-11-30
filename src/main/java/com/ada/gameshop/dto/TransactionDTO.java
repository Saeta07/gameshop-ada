package com.ada.gameshop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TransactionDTO {

    private Long id;
    private String date;
    private List<ProductDTO> productDTOS;

    public TransactionDTO (String date, List<ProductDTO> productDTOS) {
        this.date= date;
        this.productDTOS = productDTOS;
    }

}
