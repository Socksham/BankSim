package com.example.demo3.person;

import com.example.demo3.appuser.AppUser;
import com.example.demo3.bank.Bank;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class PersonService {
    private PersonRepository personRepository;
    private static final Logger LOGGER = Logger.getLogger(PersonService.class.getName());

    public PersonService(PersonRepository personRepository2){
        this.personRepository = personRepository2;
    }

    //call functions in repository
    public void save(Person person){
        if(person == null){
            LOGGER.log(Level.SEVERE, "Person is null. Are you sure you have connected your form to the application?");
            return;
        }
        this.personRepository.save(person);
    }

    public List<Person> findAllPending(Bank bank, Person.Status s){
        return this.personRepository.searchPending(bank, s);
    }

    public List<Person> findAllAccepted(Bank bank, Person.Status s){
        return this.personRepository.searchAccepted(bank, s);
    }

    public List<Person> findAllRejected(Bank bank, Person.Status s){
        return this.personRepository.searchRejected(bank, s);
    }

    public List<Person> findAll(String name){
        return this.personRepository.searchByName(name);
    }


}