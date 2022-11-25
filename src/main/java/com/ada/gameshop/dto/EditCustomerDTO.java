package com.ada.gameshop.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EditCustomerDTO {
    Long id;
    String name;
    @JsonAlias("last_name")
    String lastName;
    String email;
    String telephone;
}
