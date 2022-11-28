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
public class NewTransactionDTO {
    private Long customerId;
    private String date;
    private List<Long> productIdList;
}
