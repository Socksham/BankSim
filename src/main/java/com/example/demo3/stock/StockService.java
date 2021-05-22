package com.example.demo3.stock;

import com.example.demo3.stocktransactions.StockTransaction;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockService {
    private StockRepository stockRepository;

    //call repository functions
    public StockService(StockRepository stockRepository){
        this.stockRepository = stockRepository;
    }

    public List<Stock> findAll(){
        return this.stockRepository.findAll();
    }

    public List<Stock> findByName(String name){
        return this.stockRepository.findByName(name);
    }

    public void save(Stock stock){
        if(stock == null){
            return;
        }
        this.stockRepository.save(stock);
    }
}
