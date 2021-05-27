package com.example.demo3.accounts.savingsaccount;

import org.springframework.stereotype.Service;

@Service
public class SavingsAccountService {
    private SavingsAccountRepository savingsAccountRepository;

    public SavingsAccountService(SavingsAccountRepository savingsAccountRepository){
        this.savingsAccountRepository = savingsAccountRepository;
    }
}