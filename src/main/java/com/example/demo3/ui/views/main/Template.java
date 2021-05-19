package com.example.demo3.ui.views.main;

import com.example.demo3.appuser.AppUser;
import com.example.demo3.loan.LoanService;
import com.example.demo3.person.PersonService;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.util.Timer;

public class Template extends VerticalLayout {
    public static boolean bankState = false;
    public static Timer timer = new Timer();
    public static H1 bankNum = new H1();
    public static boolean buyRadioAdButton = false;
    public static boolean buyNewspaperAdButton = false;
    public static boolean buyTvAdButton = false;
    public static boolean buyBillboardAdButton = false;
    public static int secondsPerCustomer = 5000;
}
