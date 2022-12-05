package com.ada.gameshop.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(nullable = false)
    private LocalDate date;
    @OneToMany(mappedBy = "transaction")
    private List<TransactionDetail> transactionDetails;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    public Transaction() {
    }

    public Transaction(Long id, LocalDate date, List<TransactionDetail> transactionDetails, Customer customer) {
        this.id = id;
        this.date = date;
        this.transactionDetails = transactionDetails;
        this.customer = customer;
    }

    public Transaction(Long id, LocalDate date, Customer customer) {
        this.id = id;
        this.date = date;
        this.customer = customer;
    }
}
