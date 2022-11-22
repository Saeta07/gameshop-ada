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
public class CustomerDTO {

    @JsonAlias("id")
    private Long customerId;
    private String name;
    @JsonAlias("last_name")
    private String lastName;
    private String email;
    private String telephone;
    @JsonAlias("order_user")
    private int orderUser;
    @JsonAlias("order_id")
    private int orderId;

}
