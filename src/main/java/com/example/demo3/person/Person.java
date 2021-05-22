package com.example.demo3.person;

import com.example.demo3.accounts.checkingaccount.CreditAccount;
import com.example.demo3.accounts.investingaccount.InvestingAccount;
import com.example.demo3.accounts.savingsaccount.SavingsAccount;
import com.example.demo3.bank.Bank;
import com.example.demo3.loan.Loan;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.example.demo3.AbstractEntity;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Person extends AbstractEntity implements Cloneable {

    //status class
    public enum Status {
        ACCEPTED, REJECTED, PENDING, BANKRUPT
    }

    //all local vars
    //id generator
    @SequenceGenerator(
            name="person_sequence",
            sequenceName = "person_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "person_sequence"
    )
    private Long id;
    @ManyToOne
    private Bank bank;
    //savings account logic
    @OneToMany(mappedBy = "person", fetch = FetchType.EAGER)
    private List<Loan> loans = new LinkedList<>();
    @OneToOne(cascade = CascadeType.ALL)
    private CreditAccount creditAccount;
    @OneToOne(cascade = CascadeType.ALL)
    private SavingsAccount savingsAccount;
    @OneToOne(cascade = CascadeType.ALL)
    private InvestingAccount investingAccount;
    private String firstName;
    private String lastName;
    private int creditScore;
    private int age;
    private Status status;

    public Person(Bank bank, String firstName,
                  String lastName, int creditScore,
                  int age, CreditAccount creditAccount,
                  SavingsAccount savingsAccount, InvestingAccount investingAccount){
        this.bank = bank;
        this.firstName = firstName;
        this.lastName = lastName;
        this.creditScore = creditScore;
        this.age = age;
        this.creditAccount = creditAccount;
        this.savingsAccount = savingsAccount;
        this.investingAccount = investingAccount;
        this.status = Status.PENDING;
    }

    public Person(Bank bank, String firstName, String lastName, int creditScore, int age) {
        this.bank = bank;
        this.firstName = firstName;
        this.lastName = lastName;
        this.creditScore = creditScore;
        this.age = age;
        this.status = Status.PENDING;
    }
}
