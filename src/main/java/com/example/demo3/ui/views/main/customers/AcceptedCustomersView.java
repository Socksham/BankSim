package com.example.demo3.ui.views.main.customers;

import com.example.demo3.appuser.AppUser;
import com.example.demo3.person.Person;
import com.example.demo3.person.PersonService;
import com.example.demo3.ui.MainLayout;
import com.example.demo3.ui.views.main.Template;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

//shows all accepted customers in grid
@Component
@Scope("prototype")
@Route(value = "/acceptedcustomers", layout = MainLayout.class)
@PageTitle("Bank | Accepted Customers")
public class AcceptedCustomersView extends Template {
    PersonService personService;
    Grid<Person> grid;
    String username;
    AppUser appUser;
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    PersonForm personForm;

    public AcceptedCustomersView(PersonService personService){
        grid = new Grid<>(Person.class);
        this.personService = personService;
        System.out.println(bankState);

        setSizeFull();
        configureGrid();

        if (principal instanceof AppUser) {
            this.appUser = ((AppUser)principal);
            System.out.println(appUser.getBank());
            System.out.println(appUser.getBank());
            this.username = ((AppUser)principal).getUsername();
            System.out.println(this.username);
        }

        personForm = new PersonForm();
        personForm.addListener(PersonForm.AcceptEvent.class, this::saveContactAccept);
        personForm.addListener(PersonForm.RejectEvent.class, this::saveContactReject);
        personForm.addListener(PersonForm.CloseEvent.class, e -> closeEditor());

        Div content = new Div(grid, personForm);
        content.addClassName("content");
        content.setSizeFull();

        add(content);

        updateList();
        closeEditor();

    }

    //close person form editor
    private void closeEditor() {
        personForm.setContact(null);
        personForm.setVisible(false);
        updateList();
        removeClassName("editing");
    }

    //save and reject the contacts
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

    //set the person in the form
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
        grid.setItems(personService.findAllAccepted(appUser.getBank(), Person.Status.ACCEPTED));
    }
}
