package com.example.demo3.loan;

import com.example.demo3.appuser.AppUser;
import com.example.demo3.bank.Bank;
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
public class Loan extends AbstractEntity {

    public enum Status {
        ACCEPTED, REJECTED, PENDING, PAID
    }

    @SequenceGenerator(
            name="loan_sequence",
            sequenceName = "loan_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "loan_sequence"
    )
    private Long id;
    @ManyToOne(cascade = CascadeType.MERGE)
    private Bank bank;
    @ManyToOne(cascade = CascadeType.MERGE)
    private Person person;
    private double amountOfLoan;
    @Enumerated(EnumType.STRING)
    private Status loanRole;
    private int yearsToPay;
    private double loanRate;
    private double monthlyPayment;
    private double interest;

    public Loan(Person person, double amountOfLoan, Bank bank, int monthsToPay){
        this.person = person;
        this.amountOfLoan = amountOfLoan;
        this.bank = bank;
        this.loanRole = Status.PENDING;
        this.yearsToPay = monthsToPay;
        if(person.getCreditScore() < 630){
            this.loanRate = bank.getScoreUnder630();
        }else if(person.getCreditScore() < 690){
            this.loanRate = bank.getScoreUnder690();
        }else if(person.getCreditScore() < 720){
            this.loanRate = bank.getScoreUnder720();
        }else{
            this.loanRate = bank.getScoreUnder850();
        }
        double i = this.loanRate/12;
        this.monthlyPayment = myRound((this.amountOfLoan*i*Math.pow((1+i), this.yearsToPay*12))/((Math.pow((1+i), this.yearsToPay*12)) - 1), 2);
        this.interest = myRound(this.amountOfLoan*(1+(this.loanRate*this.yearsToPay)), 2);
        System.out.println("Monthly Payment: " + monthlyPayment + " Loan Amount: " + amountOfLoan + "Interest: " + interest + " Loan Rate: " + loanRate + " Months To Pay: " + monthsToPay);
    }

    private double myRound(double numToRound, int placeValue){
        numToRound = numToRound*(Math.pow(10,placeValue));
        numToRound = Math.round(numToRound);
        numToRound = numToRound/(Math.pow(10,placeValue));
        return numToRound;
    }
}