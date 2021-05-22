package com.example.demo3.accounts.investingaccount;

import com.example.demo3.bank.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface InvestingAccountRepository extends JpaRepository<InvestingAccount, Long> {
    //query database for items involving the parameters
    @Query("SELECT c FROM InvestingAccount c WHERE concat(c.person.bank.id, '') LIKE concat('%', :bank, '%') AND c.person.investingAccount.role = :term")
    List<InvestingAccount> findAll(@Param("bank") Bank bank, @Param("term") InvestingAccount.Status status);
}
