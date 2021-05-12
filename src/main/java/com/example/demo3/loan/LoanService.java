package com.example.demo3.loan;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
public class LoanService {
    private LoanRepository loanRepository;

    public LoanService(LoanRepository loanRepository){
        this.loanRepository = loanRepository;
    }
}
