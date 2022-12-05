package com.ada.gameshop.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TransactionDetailDTO {

    private Long id;
    @JsonAlias("quantity_items")
    private int quantityItem;
    @JsonAlias("total_price")
    private double totalPrice;
    @JsonAlias("transaction_id")
    private Long transactionId;
    @JsonAlias("product_id")
    private Long productId;

    public TransactionDetailDTO(int quantityItem, double totalPrice, Long transactionId, Long productId) {
        this.quantityItem = quantityItem;
        this.totalPrice = totalPrice;
        this.transactionId = transactionId;
        this.productId = productId;
    }

}
