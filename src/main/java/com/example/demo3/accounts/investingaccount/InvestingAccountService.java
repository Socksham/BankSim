package com.example.demo3.accounts.investingaccount;

import com.example.demo3.bank.Bank;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvestingAccountService {
    private InvestingAccountRepository investingAccountRepository;

    public InvestingAccountService(InvestingAccountRepository investingAccountRepository){
        this.investingAccountRepository = investingAccountRepository;
    }

    public List<InvestingAccount> findAll(Bank bank){
        return investingAccountRepository.findAll(bank);
    }
}
