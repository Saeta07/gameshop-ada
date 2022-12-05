package com.ada.gameshop.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "transaction_detail")
public class TransactionDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "quantity_item", nullable = false)
    private Integer quantityItem;


    @Column(name = "total_price", nullable = false)
    private Double totalPrice;

    @ManyToOne
    @JoinColumn(name = "transaction_id")
    private Transaction transaction;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public TransactionDetail() {
    }

    public TransactionDetail(Long id, Integer quantityItem, Double totalPrice, Transaction transaction,
                             Product product) {
        this.id = id;
        this.quantityItem = quantityItem;
        this.totalPrice = totalPrice;
        this.transaction = transaction;
        this.product = product;

    }

    public TransactionDetail(Integer quantityItem, Double totalPrice, Transaction transaction, Product product) {
        this.quantityItem = quantityItem;
        this.totalPrice = totalPrice;
        this.transaction = transaction;
        this.product = product;

    }

    public TransactionDetail(Integer quantityItem, Double totalPrice,Product product) {
        this.quantityItem = quantityItem;
        this.totalPrice = totalPrice;
        this.product = product;

    }

}
