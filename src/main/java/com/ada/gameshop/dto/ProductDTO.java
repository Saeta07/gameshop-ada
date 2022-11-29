package com.ada.gameshop.dto;

import com.ada.gameshop.enumerated.Developer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
public class ProductDTO {

    private Long id;
    private String name;
    private Float price;
    private Developer developer;
    private LocalDate releaseDate;

    public ProductDTO (String name, Float price, Developer developer,
                              LocalDate releaseDate) {
        this.name = name;
        this.price = price;
        this.developer = developer;
        this.releaseDate = releaseDate;
    }

}
