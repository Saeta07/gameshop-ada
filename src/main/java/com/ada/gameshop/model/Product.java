package com.ada.gameshop.model;

import com.ada.gameshop.enumerated.Category;
import com.ada.gameshop.enumerated.Developer;
import com.ada.gameshop.enumerated.Platform;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "product")
public class Product {

    @javax.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private Float price;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Developer developer;
    @Column(name = "release_date", nullable = false)
    private LocalDate releaseDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(nullable = false)
    private String pegi;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Platform platform;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String generation;

    public Product(String name, Float price, Developer developer,
                   LocalDate releaseDate, Category category, String pegi, Platform platform,
                   String type, String generation) {
        this.name = name;
        this.price = price;
        this.developer = developer;
        this.releaseDate = releaseDate;
        this.category = category;
        this.pegi = pegi;
        this.platform = platform;
        this.type = type;
        this.generation = generation;
    }

}
