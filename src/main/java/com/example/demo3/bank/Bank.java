package com.example.demo3.bank;

import com.example.demo3.AbstractEntity;
import com.example.demo3.appuser.AppUser;
import com.example.demo3.loan.Loan;
import com.example.demo3.person.Person;
import com.vaadin.flow.component.polymertemplate.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Bank extends AbstractEntity {
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
    @OneToMany(mappedBy = "bank", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Loan> loans = new LinkedList<>();
    private double scoreUnder630 = 0.085;
    private double scoreUnder690 = 0.074;
    private double scoreUnder720 = 0.066;
    private double scoreUnder850 = 0.045;
    private double money;

    public Bank(AppUser user, double money){
        this.user = user;
        this.money = money;
    }
}