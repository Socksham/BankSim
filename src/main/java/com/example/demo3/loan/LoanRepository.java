package com.example.demo3.loan;

import com.example.demo3.bank.Bank;
import com.example.demo3.person.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface LoanRepository extends JpaRepository<Loan, Long> {
    //find all pending person objects
    @Query("SELECT c FROM Loan c WHERE concat(c.bank.id, '') LIKE concat('%', :term, '%') AND c.loanRole = :pending")
    List<Loan> searchPending(@Param("term") Bank term, @Param("pending")Loan.Status pending);

    //find all accepted person objects
    @Query("SELECT c FROM Loan c WHERE concat(c.bank.id, '') LIKE concat('%', :term, '%') AND c.loanRole = :accepted")
    List<Loan> searchAccepted(@Param("term") Bank term, @Param("accepted")Loan.Status accepted);

    //find all rejected person objects
    @Query("SELECT c FROM Loan c WHERE concat(c.bank.id, '') LIKE concat('%', :term, '%') AND c.loanRole = :rejected")
    List<Loan> searchRejected(@Param("term") Bank term, @Param("rejected")Loan.Status rejected);
}