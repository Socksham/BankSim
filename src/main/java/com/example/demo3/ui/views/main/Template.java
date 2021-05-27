package com.example.demo3.ui.views.main;

import com.example.demo3.appuser.AppUser;
import com.example.demo3.loan.LoanService;
import com.example.demo3.person.Person;
import com.example.demo3.person.PersonService;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.util.ArrayList;
import java.util.Timer;

//Template to hold all shared values
public class Template extends VerticalLayout {
    public static boolean bankState = false;
    public static Timer timer = new Timer();
    public static H1 bankNum = new H1();
    public static boolean buyRadioAdButton = false;
    public static boolean buyNewspaperAdButton = false;
    public static boolean buyTvAdButton = false;
    public static boolean buyBillboardAdButton = false;
    public static int secondsPerCustomer = 5000;
    public static int months = 0;

    public static ArrayList<Person> peopleAcceptedRecently = new ArrayList<Person>();

    private double myRound(double numToRound, int placeValue){
        numToRound = numToRound*(Math.pow(10, placeValue));
        numToRound = Math.round(numToRound);
        numToRound = numToRound/(Math.pow(10, placeValue));
        return numToRound;
    }
}