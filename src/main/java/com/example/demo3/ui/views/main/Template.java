package com.example.demo3.ui.views.main;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.util.Timer;

public class Template extends VerticalLayout {
    public static boolean bankState = false;
    public static Timer timer = new Timer();
    public static H1 bankNum = new H1();
}
