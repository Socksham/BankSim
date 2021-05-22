package com.example.demo3.stock;

import com.example.demo3.AbstractEntity;
import com.example.demo3.bank.Bank;
import com.example.demo3.stocktransactions.StockTransaction;
import com.vaadin.flow.component.polymertemplate.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Stock extends AbstractEntity {
    //local vars
    //id generator
    @SequenceGenerator(
            name="stock_sequence",
            sequenceName = "stock_sequence"
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "stock_sequence"
    )
    private Long id;
    private String stock;
    private double pricePerStock;
    private String open;
    private String high;
    private String low;
    private String changePercent;

    public Stock(String stock, double pricePerStock, String open, String high, String low, String changePercent) {
        this.stock = stock;
        this.pricePerStock = pricePerStock;
        this.open = open;
        this.high = high;
        this.low = low;
        this.changePercent = changePercent;
    }
}
