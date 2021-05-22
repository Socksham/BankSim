package com.example.demo3.accounts.investingaccount;

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
public class InvestingAccount extends AbstractEntity {
    //role enums
    public enum Status {
        ACCEPTED, REJECTED, PENDING
    }

    //id generator
    @SequenceGenerator(
            name="investing_sequence",
            sequenceName = "investing_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "investing_sequence"
    )
    private Long id;
    //connection to person
    @OneToOne(cascade = CascadeType.ALL)
    private Person person;
    //money amount variable
    private double amountOfMoney;
    //role variable
    @Enumerated(EnumType.STRING)
    private InvestingAccount.Status role;

    public InvestingAccount(Person person, double amountOfMoney){
        this.person = person;
        this.amountOfMoney = amountOfMoney;
        this.role = InvestingAccount.Status.PENDING;
    }
}
