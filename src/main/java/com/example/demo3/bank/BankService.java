package com.example.demo3.bank;

import com.example.demo3.loan.Loan;
import com.example.demo3.loan.LoanRepository;
import com.example.demo3.person.PersonService;
import org.springframework.stereotype.Service;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class BankService {
    private BankRepository bankRepository;
    private static final Logger LOGGER = Logger.getLogger(PersonService.class.getName());

    public void save(Bank person){
        if(person == null){
            LOGGER.log(Level.SEVERE, "Person is null. Are you sure you have connected your form to the application?");
            return;
        }
        this.bankRepository.save(person);
    }
}
