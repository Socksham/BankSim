package com.example.demo3.accounts.checkingaccount;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

//repository for credit accounts
@Repository
@Transactional(readOnly = true)
public interface CreditAccountRepository extends JpaRepository<CreditAccount, Long> {

}
