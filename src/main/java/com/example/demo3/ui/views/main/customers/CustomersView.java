package com.example.demo3.ui.views.main.customers;

import com.example.demo3.accounts.checkingaccount.CreditAccount;
import com.example.demo3.accounts.investingaccount.InvestingAccount;
import com.example.demo3.accounts.savingsaccount.SavingsAccount;
import com.example.demo3.appuser.AppUser;
import com.example.demo3.loan.Loan;
import com.example.demo3.loan.LoanService;
import com.example.demo3.person.Person;
import com.example.demo3.person.PersonService;
import com.example.demo3.ui.MainLayout;
import com.example.demo3.ui.views.main.Template;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

//all customers view
@Component
@Scope("prototype")
@Route(value = "/customers", layout = MainLayout.class)
@PageTitle("Bank | Customers")
public class CustomersView extends Template {
    PersonService personService;
    LoanService loanService;
    PersonForm personForm;
    Button refresh = new Button("Refresh");
    Grid<Person> grid;
    String username;
    AppUser appUser;
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    public CustomersView(PersonService personService, LoanService loanService) {
        grid = new Grid<>(Person.class);
        this.personService = personService;
        this.loanService = loanService;
        setSizeFull();
        configureGrid();

        if (principal instanceof AppUser) {
            this.appUser = ((AppUser)principal);
            System.out.println(appUser.getBank());
            System.out.println(appUser.getBank());
            this.username = ((AppUser)principal).getUsername();
            System.out.println(this.username);
        }

        refresh.addClickListener(click -> {
            updateList();
        });

        //form to show person details
        personForm = new PersonForm();
        personForm.addListener(PersonForm.AcceptEvent.class, this::saveContactAccept);
        personForm.addListener(PersonForm.RejectEvent.class, this::saveContactReject);
        personForm.addListener(PersonForm.CloseEvent.class, e -> closeEditor());

        Div content = new Div(grid, personForm);
        content.addClassName("content");
        content.setSizeFull();

        add(content, refresh);

        updateList();
        closeEditor();
    }

    //save and reject contacts
    private void saveContactAccept(PersonForm.AcceptEvent evt) {
        evt.getContact().setStatus(Person.Status.ACCEPTED);
        evt.getContact().getSavingsAccount().setRole(SavingsAccount.Status.ACCEPTED);
        evt.getContact().getCreditAccount().setRole(CreditAccount.Status.ACCEPTED);
        try{
            evt.getContact().getInvestingAccount().setRole(InvestingAccount.Status.ACCEPTED);
            appUser.getBank().setInvestingAccountValue(appUser.getBank().getInvestingAccountValue() + evt.getContact().getInvestingAccount().getAmountOfMoney());
        }catch (NullPointerException n){
        }
        personService.save(evt.getContact());
        updateList();
        closeEditor();
        peopleAcceptedRecently.add(evt.getContact());
    }
    private void saveContactReject(PersonForm.RejectEvent evt) {
        evt.getContact().setStatus(Person.Status.REJECTED);
        evt.getContact().getSavingsAccount().setRole(SavingsAccount.Status.REJECTED);
        evt.getContact().getCreditAccount().setRole(CreditAccount.Status.REJECTED);
        try{
            evt.getContact().getInvestingAccount().setRole(InvestingAccount.Status.REJECTED);
        }catch (NullPointerException n){
        }

        personService.save(evt.getContact());
        updateList();
        closeEditor();
    }

    //close the editor
    private void closeEditor() {
        personForm.setContact(null);
        personForm.setVisible(false);
        updateList();
        removeClassName("editing");
    }

    //configure grid columns
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

    //set person in form
    private void editPerson(Person contact) {
        if (contact == null) {
            closeEditor();
        } else {
            personForm.setContact(contact);
            personForm.setVisible(true);
            addClassName("editing");
        }
    }

    //update list on button click
    private void updateList() {
        grid.setItems(personService.findAllPending(appUser.getBank(), Person.Status.PENDING));
    }
}
