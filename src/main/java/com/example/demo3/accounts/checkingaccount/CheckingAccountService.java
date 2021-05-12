package com.example.demo3.accounts.checkingaccount;

import org.springframework.stereotype.Service;

@Service
public class CheckingAccountService {
    private CheckingAccountRepository checkingAccountRepository;

    public CheckingAccountService(CheckingAccountRepository checkingAccountRepository){
        this.checkingAccountRepository = checkingAccountRepository;
    }


}
