package com.example.demo3.ui.views.main.loans;

import com.example.demo3.appuser.AppUser;
import com.example.demo3.loan.Loan;
import com.example.demo3.loan.LoanService;
import com.example.demo3.person.PersonService;
import com.example.demo3.ui.MainLayout;
import com.example.demo3.ui.views.main.Template;
import com.example.demo3.ui.views.main.customers.PersonForm;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

//shows all accepted loans
@Component
@Scope("prototype")
@Route(value = "/acceptedloans", layout = MainLayout.class)
@PageTitle("Bank | Accepted Loans")
public class AcceptedLoansView extends Template {
    LoanService loanService;
    PersonService personService;
    String username;
    AppUser appUser;
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    Button refresh = new Button("Refresh");

    Grid<Loan> grid;

    public AcceptedLoansView(LoanService loanService, PersonService personService){
        grid = new Grid<>(Loan.class);
        this.loanService = loanService;
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

        refresh.addClickListener(click -> {
            updateList();
        });

        add(grid, refresh);

        updateList();

    }

    //configure grid columns
    private void configureGrid() {
        grid.addClassName("contact-grid");
        grid.setSizeFull();
        grid.setColumns("person.firstName", "person.creditScore", "person.age", "amountOfLoan", "yearsToPay", "loanRole");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
    }

    //update list on button click
    private void updateList() {
        grid.setItems(loanService.findAllAccepted(appUser.getBank(), Loan.Status.ACCEPTED));
    }

}
