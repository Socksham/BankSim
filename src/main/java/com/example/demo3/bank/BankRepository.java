package com.example.demo3.bank;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

//repository connected to databsase
@Repository
@Transactional(readOnly = true)
public interface BankRepository extends JpaRepository<Bank, Long> {
}