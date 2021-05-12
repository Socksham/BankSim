package com.example.demo3.accounts.investingaccount;

import org.springframework.stereotype.Service;

@Service
public class InvestingAccountService {
    private InvestingAccountRepository investingAccountRepository;

    public InvestingAccountService(InvestingAccountRepository investingAccountRepository){
        this.investingAccountRepository = investingAccountRepository;
    }
}
