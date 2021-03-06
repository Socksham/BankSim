package com.example.demo3.bank;

import com.example.demo3.AbstractEntity;
import com.example.demo3.appuser.AppUser;
import com.example.demo3.loan.Loan;
import com.example.demo3.person.Person;
import com.example.demo3.stocktransactions.StockTransaction;
import com.vaadin.flow.component.polymertemplate.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Bank extends AbstractEntity {

    //local vars here
    //id generator
    @SequenceGenerator(
            name="bank_sequence",
            sequenceName = "bank_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "bank_sequence"
    )
    private Long id;
    @OneToOne(cascade = CascadeType.ALL)
    private AppUser user;
    @OneToMany(mappedBy = "bank", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Person> persons = new LinkedList<>();
    @OneToMany(mappedBy = "bank", cascade = CascadeType.ALL)
    private List<Loan> loans = new LinkedList<>();
    @OneToMany(mappedBy = "bank", cascade = CascadeType.ALL)
    private List<StockTransaction> stocks = new LinkedList<>();
    private double scoreUnder630 = 0.085;
    private double scoreUnder690 = 0.074;
    private double scoreUnder720 = 0.066;
    private double scoreUnder850 = 0.045;
    private double money;
    private double investingAccountValue;
    private ArrayList<Double> moneyPerMonth = new ArrayList<Double>();

    public Bank(AppUser user, double money){
        this.user = user;
        this.money = money;
        this.investingAccountValue = 0.0;
        moneyPerMonth.add(money);
    }

    //simple add money rather than needing to use getter and setters
    public void addToMoneyStat(Double amount){
        moneyPerMonth.add(amount);
    }
}