package com.example.demo3.person;

import com.example.demo3.bank.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    //find all pending
    @Query("SELECT c FROM Person c WHERE concat(c.bank.id, '') LIKE concat('%', :term, '%') AND c.status = :pending")
    List<Person> searchPending(@Param("term")Bank term, @Param("pending")Person.Status pending);

    //find all accepted
    @Query(value = "SELECT c FROM Person c WHERE concat(c.bank.id, '') LIKE concat('%', :term, '%') AND c.status = :accepted")
    List<Person> searchAccepted(@Param("term")Bank term, @Param("accepted")Person.Status accepted);

    //find all rejected
    @Query("SELECT c FROM Person c WHERE concat(c.bank.id, '') LIKE concat('%', :term, '%') AND c.status = :rejected")
    List<Person> searchRejected(@Param("term")Bank term, @Param("rejected")Person.Status rejected);

    //find by name
    @Query("SELECT c FROM Person c WHERE c.firstName LIKE %?1%")
    List<Person> searchByName(String term);
}
