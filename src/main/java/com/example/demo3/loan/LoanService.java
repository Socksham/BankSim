package com.example.demo3.loan;

import com.example.demo3.bank.Bank;
import com.example.demo3.person.Person;
import com.example.demo3.person.PersonService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class LoanService {
    private LoanRepository loanRepository;
    private static final Logger LOGGER = Logger.getLogger(PersonService.class.getName());

    public LoanService(LoanRepository loanRepository){
        this.loanRepository = loanRepository;
    }

    public List<Loan> findAllPending(Bank bank, Loan.Status s){
        return this.loanRepository.searchPending(bank, s);
    }
    public List<Loan> findAllAccepted(Bank bank, Loan.Status s){
        return this.loanRepository.searchAccepted(bank, s);
    }

    public void save(Loan person){
        if(person == null){
            LOGGER.log(Level.SEVERE, "Person is null. Are you sure you have connected your form to the application?");
            return;
        }
        this.loanRepository.save(person);
    }

}
