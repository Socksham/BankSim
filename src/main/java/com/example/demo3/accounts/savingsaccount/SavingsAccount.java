package com.example.demo3.accounts.savingsaccount;

import com.example.demo3.accounts.checkingaccount.CreditAccount;
import com.example.demo3.appuser.AppUser;
import com.example.demo3.person.Person;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.example.demo3.AbstractEntity;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class SavingsAccount extends AbstractEntity {
    //role class
    public enum Status {
        ACCEPTED, REJECTED, PENDING
    }

    //id generator
    @SequenceGenerator(
            name="savings_sequence",
            sequenceName = "savings_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "savings_sequence"
    )
    private Long id;
    //person class connection
    @ManyToOne(cascade = CascadeType.ALL)
    private Person person;
    //money amount variable
    private double amountOfMoney;
    //role variable
    @Enumerated(EnumType.STRING)
    private SavingsAccount.Status role;

    public SavingsAccount(Person person, double amountOfMoney){
        this.person = person;
        this.amountOfMoney = amountOfMoney;
        this.role = SavingsAccount.Status.PENDING;
    }
}