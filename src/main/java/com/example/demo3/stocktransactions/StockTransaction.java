package com.example.demo3.stocktransactions;

import com.example.demo3.AbstractEntity;
import com.example.demo3.bank.Bank;
import com.vaadin.flow.component.polymertemplate.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class StockTransaction extends AbstractEntity {

    public enum Status {
        BUY, SELL
    }

    @SequenceGenerator(
            name="stocks_sequence",
            sequenceName = "stocks_sequence"
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "stocks_sequence"
    )
    private Long id;
    @ManyToOne(cascade = CascadeType.ALL)
    private Bank bank;
    private String stock;
    private double pricePerStock;
    private int amount_of_stock;
    @Enumerated(EnumType.STRING)
    private Status transactionRole;

    public StockTransaction(Bank bank, String stock, double pricePerStock, int amount_of_stock, Status transactionRole){
        this.bank = bank;
        this.stock = stock;
        this.pricePerStock = pricePerStock;
        this.amount_of_stock = amount_of_stock;
        this.transactionRole = transactionRole;
    }
}
