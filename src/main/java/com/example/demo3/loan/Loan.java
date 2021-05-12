package com.example.demo3.loan;

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
public class Loan extends AbstractEntity {
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
    @ManyToOne(cascade = CascadeType.ALL)
    private Person person;
    private double amountOfLoan;
    @Enumerated(EnumType.STRING)
    private LoanRole loanRole;

    public Loan(AppUser user, Person person, double amountOfLoan, LoanRole loanRole){
        this.person = person;
        this.amountOfLoan = amountOfLoan;
        this.loanRole = loanRole;
    }
}
