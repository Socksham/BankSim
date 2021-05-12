package com.example.demo3.person;

import com.example.demo3.bank.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    @Query("SELECT c FROM Person c WHERE concat(c.bank.id, '') LIKE concat('%', :term, '%')")
    List<Person> search(@Param("term")Bank term);

    @Query("SELECT c FROM Person c WHERE concat(c.bank.id, '') LIKE concat('%', :term, '%') AND concat(c.status, '') LIKE 'Accepted'")
    List<Person> searchAccepted(@Param("term")Bank term);

    @Query("SELECT c FROM Person c WHERE c.firstName LIKE %?1%")
    List<Person> searchByName(String term);
}
