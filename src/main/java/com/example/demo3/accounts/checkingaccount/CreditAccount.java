package com.example.demo3.accounts.checkingaccount;

import com.example.demo3.AbstractEntity;
import com.example.demo3.appuser.AppUser;
import com.example.demo3.loan.Loan;
import com.example.demo3.person.Person;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class CreditAccount extends AbstractEntity {

    //status for role
    public enum Status {
        ACCEPTED, REJECTED, PENDING
    }

    //id generator
    @SequenceGenerator(
            name="checking_sequence",
            sequenceName = "checking_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "checking_sequence"
    )
    private Long id;
    //connection to person class
    @OneToOne(cascade = CascadeType.ALL)
    private Person person;
    //money amount variable
    private double amountOfMoney;
    //role variable
    @Enumerated(EnumType.STRING)
    private CreditAccount.Status role;

    public CreditAccount(Person person, double amountOfMoney){
        this.person = person;
        this.amountOfMoney = amountOfMoney;
        this.role = Status.PENDING;
    }
}
