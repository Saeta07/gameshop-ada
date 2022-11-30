package com.ada.gameshop.dto;

import com.ada.gameshop.enumerated.Category;
import com.ada.gameshop.enumerated.Developer;
import com.ada.gameshop.enumerated.Platform;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductDTO {

    private Long id;
    private String name;
    private Float price;
    private Developer developer;
    private String releaseDate;
    private Category category;
    private String pegi;
    private Platform platform;
    private String type;
    private String generation;

    public ProductDTO (String name, Float price, Developer developer,
                       String releaseDate, Category category,
                       String pegi, Platform platform,
                       String type, String generation) {
        this.name = name;
        this.price = price;
        this.developer = developer;
        this.releaseDate = releaseDate;
        this.category = category;
        this.pegi = pegi;
        this. platform = platform;
        this.type = type;
        this.generation = generation;
    }
}
