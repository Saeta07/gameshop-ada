package com.ada.gameshop.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Builder
public class CustomerDTO {

    @JsonAlias("id")
    private Long customerId;
    private String name;
    @JsonAlias("last_name")
    private String lastName;
    private String email;
    private String telephone;

    private List<TransactionDTO> transactionDTOS;

    public CustomerDTO(Long customerId, String name, String lastName,
                     String email, String telephone,
                     List<TransactionDTO> transactionDTOS) {
        this.customerId = customerId;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.telephone = telephone;
        this.transactionDTOS = transactionDTOS;
    }
    public List<TransactionDTO> getTransactionDTOS() {
        return transactionDTOS;
    }
}
