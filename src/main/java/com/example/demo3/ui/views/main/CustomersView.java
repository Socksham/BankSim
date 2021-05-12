package com.example.demo3.ui.views.main;

import com.example.demo3.accounts.checkingaccount.CheckingAccount;
import com.example.demo3.appuser.AppUser;
import com.example.demo3.bank.Bank;
import com.example.demo3.bank.BankService;
import com.example.demo3.person.Person;
import com.example.demo3.person.PersonService;
import com.example.demo3.ui.MainLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.Command;
import com.vaadin.flow.server.VaadinSession;
import elemental.json.impl.JsonUtil;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Timer;
import java.util.TimerTask;

@Component
@Scope("prototype")
@Route(value = "/customers", layout = MainLayout.class)
@PageTitle("Bank | Customers")
public class CustomersView extends Template {
    //add button to end people adding like close bank
    PersonService personService;
    PersonForm personForm;

    Button openBank = new Button("Open Bank");
    Button refresh = new Button("Refresh");
    Grid<Person> grid;
//    Boolean bankState = false;
    String username;
    AppUser appUser;
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    class addPerson extends TimerTask {
        public void run() {
            personService.save(new Person(appUser.getBank(), "ANDY", "DALTON", (int)(Math.random() * ((850 - 300) + 1))+300, (int)(Math.random() * ((65 - 18) + 1))+18));
            System.out.println(personService.findAll(appUser.getBank()));
        }
    }

    public CustomersView(PersonService personService) {
        grid = new Grid<>(Person.class);
        this.personService = personService;
        setSizeFull();
        configureGrid();

        if (principal instanceof AppUser) {
            this.appUser = ((AppUser)principal);
            System.out.println(appUser.getBank());
            System.out.println(appUser.getBank());
            this.username = ((AppUser)principal).getUsername();
            System.out.println(this.username);
        }

        openBank.addClickListener(click -> {
            changeState();
        });

        if(bankState){
            openBank.setText("Close Bank");
        }else{
            openBank.setText("Open Bank");
        }

        refresh.addClickListener(click -> {
            grid.setItems(personService.findAll(appUser.getBank()));
        });

        personForm = new PersonForm();
        personForm.addListener(PersonForm.AcceptEvent.class, this::saveContactAccept);
        personForm.addListener(PersonForm.RejectEvent.class, this::saveContactReject);
        personForm.addListener(PersonForm.CloseEvent.class, e -> closeEditor());

        Div content = new Div(grid, personForm);
        content.addClassName("content");
        content.setSizeFull();
//
//        expand(grid);
        add(openBank, content, refresh);

        updateList();
        closeEditor();
    }

    private void saveContactAccept(PersonForm.AcceptEvent evt) {
        evt.getContact().setStatus(Person.Status.ACCEPTED);
        personService.save(evt.getContact());
        updateList();
        closeEditor();
    }
    private void saveContactReject(PersonForm.RejectEvent evt) {
        evt.getContact().setStatus(Person.Status.REJECTED);
        personService.save(evt.getContact());
        updateList();
        closeEditor();
    }

    private void closeEditor() {
        personForm.setContact(null);
        personForm.setVisible(false);
        grid.setItems(personService.findAll(appUser.getBank()));
        removeClassName("editing");
    }

    private void configureGrid() {
        grid.addClassName("contact-grid");
        grid.setSizeFull();
        grid.setColumns("firstName", "lastName", "creditScore", "age");
        grid.addColumn(person -> {
            Person.Status string = person.getStatus();
            return string == null ? "-" : person.getStatus();
        }).setHeader("Status");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.asSingleSelect().addValueChangeListener(evt -> editPerson(evt.getValue()));

    }

    private void editPerson(Person contact) {
        if (contact == null) {
            closeEditor();
        } else {
            personForm.setContact(contact);
            personForm.setVisible(true);
            addClassName("editing");
        }
    }

    private void changeState(){
        if(bankState){
            bankState = false;
            openBank.setText("Open Bank");
            timer.cancel();
            timer = new Timer();
        }else{
            openBank.setText("Close Bank");
            bankState = true;
            timer.schedule(new addPerson(), 0, 5000);
        }
    }
    private void updateList() {
        grid.setItems(personService.findAll(appUser.getBank()));
    }
}
