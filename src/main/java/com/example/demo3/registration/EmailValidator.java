package com.example.demo3.registration;

import org.springframework.stereotype.Service;

import java.util.function.Predicate;

//not used
@Service
public class EmailValidator implements Predicate<String> {

    @Override
    public boolean test(String s) {
        return false;
    }
}
