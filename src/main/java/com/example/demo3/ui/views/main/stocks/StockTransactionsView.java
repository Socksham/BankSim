package com.example.demo3.ui.views.main.stocks;

import com.example.demo3.appuser.AppUser;
import com.example.demo3.stock.Stock;
import com.example.demo3.stocktransactions.StockTransaction;
import com.example.demo3.stocktransactions.StockTransactionService;
import com.example.demo3.ui.MainLayout;
import com.example.demo3.ui.views.main.Template;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.json.JSONException;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.io.IOException;

//Stock Transactions
@Component
@Scope("prototype")
@Route(value = "/stocktransactions", layout = MainLayout.class)
@PageTitle("Bank | Stock Transactions")
public class StockTransactionsView extends Template {
    StockTransactionService stockTransactionService;
    Grid<StockTransaction> grid = new Grid<>(StockTransaction.class);
    String username;
    AppUser appUser;
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    Button refresh = new Button("Refresh");

    public StockTransactionsView(StockTransactionService stockTransactionService){
        this.stockTransactionService = stockTransactionService;

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

    //configure grid columns
    private void configureGrid(){
        grid.setSizeFull();
        grid.setColumns("stock", "pricePerStock", "amount_of_stock", "transactionRole");
    }

    //update the list on button click
    private void updateList() {
        grid.setItems(stockTransactionService.findAll(appUser.getBank()));
    }
}