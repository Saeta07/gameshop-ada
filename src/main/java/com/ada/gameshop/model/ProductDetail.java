package com.ada.gameshop.model;

import com.ada.gameshop.enumerated.Category;
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

@Entity
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "product_detail")
public class ProductDetail {

    @javax.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Category category;
    @Column(nullable = false)
    private String pegi;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Platform platform;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
