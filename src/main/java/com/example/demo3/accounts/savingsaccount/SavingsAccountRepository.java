package com.example.demo3.accounts.savingsaccount;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface SavingsAccountRepository extends JpaRepository<SavingsAccount, Long> {
}
