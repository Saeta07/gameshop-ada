package com.ada.gameshop.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Builder
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String telephone;
    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY,
            cascade = CascadeType.REMOVE)
    private List<Transaction> transactions;

    public List<Transaction> getTransactions() {
        if (transactions == null) {
            transactions = new ArrayList<>();
        }
        return transactions;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public final void removeTransaction(final Transaction transaction){

        this.transactions.remove(transaction);
    }

    public Customer(Long id, String name, String lastName,
                  String email, String telephone) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.telephone = telephone;
    }

    public Customer(Long id, String name, String lastName,
                    String email, String telephone, List<Transaction> transactions) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.telephone = telephone;
        this.transactions = transactions;
    }

    public void modifyAttributeValue(String attributeName, Object newValue) {
        switch (attributeName) {
            case "name":
                this.name = (String) newValue;
                break;
            case "last_name":
                this.lastName = (String) newValue;
                break;
            case "email":
                this.email = (String) newValue;
                break;
            case "telephone":
                this.telephone = (String) newValue;
                break;
        }
    }

}
