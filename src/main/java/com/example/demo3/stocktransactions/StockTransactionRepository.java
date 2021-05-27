package com.example.demo3.stocktransactions;

import com.example.demo3.bank.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

//repository connected to database
@Repository
@Transactional(readOnly = true)
public interface StockTransactionRepository extends JpaRepository<StockTransaction, Long> {
    @Query("SELECT c FROM StockTransaction c WHERE concat(c.bank.id, '') LIKE concat('%', :bank, '%') AND c.stock LIKE :name")
    List<StockTransaction> findByName(@Param("bank") Bank bank, @Param("name") String name);

    @Query("SELECT c FROM StockTransaction c WHERE concat(c.bank.id, '') LIKE concat('%', :bank, '%') AND c.transactionRole = :transaction")
    List<StockTransaction> findByTransactionType(@Param("bank") Bank bank, @Param("transaction") StockTransaction.Status transaction);

    @Query("SELECT c FROM StockTransaction c WHERE concat(c.bank.id, '') LIKE concat('%', :bank, '%')")
    List<StockTransaction> findAll(@Param("bank") Bank bank);
}