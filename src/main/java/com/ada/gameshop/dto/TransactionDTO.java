package com.ada.gameshop.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class TransactionDTO {

    private Long id;
    @JsonAlias("date")
    private String date;
    @JsonAlias("customer_id")
    private Long customerId;
    private List<TransactionDetailDTO> transactionDetailDTOS;

    public TransactionDTO(Long id, String date, Long customerId) {
        this.id = id;
        this.date = date;
        this.customerId = customerId;
    }

    public TransactionDTO(Long id, String date, Long customerId, List<TransactionDetailDTO> transactionDetailDTOS) {
        this.id = id;
        this.date = date;
        this.customerId = customerId;
        this.transactionDetailDTOS = transactionDetailDTOS;
    }

}
