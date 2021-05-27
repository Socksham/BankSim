package com.example.demo3.ui.views.main.loans;

import com.example.demo3.appuser.AppUser;
import com.example.demo3.loan.Loan;
import com.example.demo3.loan.LoanService;
import com.example.demo3.person.PersonService;
import com.example.demo3.ui.MainLayout;
import com.example.demo3.ui.views.main.customers.PersonForm;
import com.example.demo3.ui.views.main.Template;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

//show all loans pending in grid
@Component
@Scope("prototype")
@Route(value = "/loans", layout = MainLayout.class)
@PageTitle("Bank | Loans")
public class LoansView extends Template {
    LoanService loanService;
    LoanForm loanForm;
    PersonService personService;
    String username;
    AppUser appUser;
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    Button refresh = new Button("Refresh");
    Notification notification = new Notification("Not enough funds to accept loan!", 3000, Notification.Position.BOTTOM_END);
    Grid<Loan> grid;

    public LoansView(LoanService loanService, PersonService personService){
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

        loanForm = new LoanForm();
        loanForm.addListener(LoanForm.AcceptEvent.class, this::saveLoanAccept);
        loanForm.addListener(LoanForm.RejectEvent.class, this::saveLoanReject);
        loanForm.addListener(PersonForm.CloseEvent.class, e -> closeEditor());

        //div with grid and form
        Div content = new Div(grid, loanForm);
        content.addClassName("content");
        content.setSizeFull();

        add(content, refresh);

        updateList();
        closeEditor();
    }

    //save and reject loans
    private void saveLoanAccept(LoanForm.AcceptEvent evt) {
        if(appUser.getBank().getMoney() - evt.getContact().getAmountOfLoan() > 0){
            evt.getContact().setLoanRole(Loan.Status.ACCEPTED);
            loanService.save(evt.getContact());
            appUser.getBank().setMoney(appUser.getBank().getMoney() - evt.getContact().getAmountOfLoan());

        }else{
            notification.open();
        }
        updateList();
        closeEditor();
        resetNum();
    }
    private void saveLoanReject(LoanForm.RejectEvent evt) {
        evt.getContact().setLoanRole(Loan.Status.REJECTED);
        loanService.save(evt.getContact());
        updateList();
        closeEditor();
    }

    //cinfigure grid columns
    private void configureGrid() {
        grid.addClassName("contact-grid");
        grid.setSizeFull();
        grid.setColumns("person.firstName", "person.creditScore", "amountOfLoan", "loanRate", "monthlyPayment", "yearsToPay", "interest", "loanRole");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.asSingleSelect().addValueChangeListener(evt -> editPerson(evt.getValue()));

    }

    //set loanForm loan
    private void editPerson(Loan contact) {
        if (contact == null) {
            closeEditor();
        } else {
            loanForm.setContact(contact);
            loanForm.setVisible(true);
            addClassName("editing");
        }
    }

    //close editor
    private void closeEditor() {
        loanForm.setContact(null);
        loanForm.setVisible(false);
        updateList();
        removeClassName("editing");
    }

    //reset bankNum variable
    private void resetNum(){
        bankNum.setText("Amount: " + appUser.getBank().getMoney());
    }

    //update list on button click
    private void updateList() {
        grid.setItems(loanService.findAllPending(appUser.getBank(), Loan.Status.PENDING));
    }
}