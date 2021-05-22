package com.example.demo3.ui.views.main.accounts;

import com.example.demo3.accounts.investingaccount.InvestingAccount;
import com.example.demo3.accounts.investingaccount.InvestingAccountService;
import com.example.demo3.appuser.AppUser;
import com.example.demo3.stocktransactions.StockTransaction;
import com.example.demo3.stocktransactions.StockTransactionService;
import com.example.demo3.ui.MainLayout;
import com.example.demo3.ui.views.main.Template;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

//investing accounts view
@Component
@Scope("prototype")
@Route(value = "/investingaccounts", layout = MainLayout.class)
@PageTitle("Bank | Investing Accounts")
public class InvestingAccountsView extends Template {
    InvestingAccountService investingAccountService;
    Grid<InvestingAccount> grid = new Grid<>(InvestingAccount.class);
    String username;
    AppUser appUser;
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    Button refresh = new Button("Refresh");

    public InvestingAccountsView(InvestingAccountService investingAccountService){
        this.investingAccountService = investingAccountService;

        setSizeFull();

        if(principal instanceof AppUser){
            this.appUser = ((AppUser) principal);
            this.username = appUser.getUsername();
        }

        refresh.addClickListener(click -> updateList());


        configureGrid();

        add(grid, refresh);

        updateList();

    }

    //configure the grid and set columns
    private void configureGrid(){
        grid.setSizeFull();
        grid.setColumns("person.firstName", "person.lastName", "amountOfMoney");
    }

    //update list on refresh button click
    private void updateList() {
        grid.setItems(investingAccountService.findAll(appUser.getBank(), InvestingAccount.Status.ACCEPTED));
    }
}
