package com.ada.gameshop.repository;

import com.ada.gameshop.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Transaction, Long> {
}
