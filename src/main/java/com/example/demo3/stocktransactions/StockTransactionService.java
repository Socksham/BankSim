package com.example.demo3.stocktransactions;

import com.example.demo3.bank.Bank;
import com.example.demo3.loan.Loan;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Level;

@Service
public class StockTransactionService {
    private StockTransactionRepository stockTransactionRepository;

    //call repository functions
    public StockTransactionService(StockTransactionRepository stockTransactionRepository){
        this.stockTransactionRepository = stockTransactionRepository;
    }

    public List<StockTransaction> findAll(Bank bank){
        return this.stockTransactionRepository.findAll(bank);
    }

    public List<StockTransaction> findByTransactionType(Bank bank, StockTransaction.Status status){
        return this.stockTransactionRepository.findByTransactionType(bank, status);
    }

    public List<StockTransaction> findByName(Bank bank, String name){
        return this.stockTransactionRepository.findByName(bank, name);
    }

    public void save(StockTransaction stockTransaction){
        if(stockTransaction == null){
            return;
        }
        this.stockTransactionRepository.save(stockTransaction);
    }
}