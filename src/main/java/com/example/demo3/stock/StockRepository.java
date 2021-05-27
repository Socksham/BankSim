package com.example.demo3.stock;

import com.example.demo3.bank.Bank;
import com.example.demo3.stocktransactions.StockTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

//repository connected to database
public interface StockRepository extends JpaRepository<Stock, Long> {
    @Query("SELECT c FROM Stock c WHERE lower(c.stock) LIKE lower(concat('%', :name, '%'))")
    List<Stock> findByName(@Param("name") String name);

}