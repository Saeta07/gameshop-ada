package com.ada.gameshop.dto;

import com.ada.gameshop.enumerated.Category;
import com.ada.gameshop.enumerated.Developer;
import com.ada.gameshop.enumerated.Platform;
import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductDTO {

    @JsonAlias("id")
    private Long productId;
    private String name;
    private Float price;
    private Developer developer;
    @JsonAlias("release_date")
    private String releaseDate;
    private Category category;
    private String pegi;
    private Platform platform;
    private String type;
    private String generation;
    private List<TransactionDetailDTO> transactionDetailDTOS;

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

    public ProductDTO (Long productId, String name, Float price, Developer developer,
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
