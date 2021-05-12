package com.example.demo3.accounts.investingaccount;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface InvestingAccountRepository extends JpaRepository<InvestingAccount, Long> {
}
