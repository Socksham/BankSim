package com.example.demo3.ui.views.main;

import com.example.demo3.person.PersonService;
import com.example.demo3.ui.MainLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@Route(value = "/acceptedcustomers", layout = MainLayout.class)
@PageTitle("Bank | Accepted Customers")
public class AcceptedCustomersView extends Template{
    PersonService personService;

    public AcceptedCustomersView(PersonService personService){
        this.personService = personService;
        System.out.println(bankState);
    }
}
