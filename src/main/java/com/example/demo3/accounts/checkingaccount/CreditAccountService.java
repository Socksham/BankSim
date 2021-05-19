package com.example.demo3.accounts.checkingaccount;

import org.springframework.stereotype.Service;

@Service
public class CreditAccountService {
    private CreditAccountRepository creditAccountRepository;

    public CreditAccountService(CreditAccountRepository creditAccountRepository){
        this.creditAccountRepository = creditAccountRepository;
    }


}
