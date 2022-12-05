package com.ada.gameshop.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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


    public CustomerDTO(Long customerId, String name, String lastName,
                     String email, String telephone) {
        this.customerId = customerId;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.telephone = telephone;
    }


}
