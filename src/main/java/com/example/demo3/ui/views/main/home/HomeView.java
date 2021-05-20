package com.example.demo3.ui.views.main.home;

import com.example.demo3.appuser.AppUser;
import com.example.demo3.loan.LoanService;
import com.example.demo3.person.Person;
import com.example.demo3.person.PersonService;
import com.example.demo3.ui.MainLayout;
import com.example.demo3.ui.views.main.customers.PersonForm;
//import com.vaadin.addon.charts.Chart;
//import com.vaadin.addon.charts.model.ChartType;
//import com.vaadin.addon.charts.model.DataSeries;
//import com.vaadin.addon.charts.model.DataSeriesItem;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.ChartType;
import com.vaadin.flow.component.charts.model.DataSeries;
import com.vaadin.flow.component.charts.model.DataSeriesItem;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Component
@Scope("prototype")
@Route(value = "", layout = MainLayout.class)
@PageTitle("Bank | Home")
public class HomeView extends VerticalLayout {
    PersonService personService;
    LoanService loanService;
    Button refresh = new Button("Refresh");
    String username;
    AppUser appUser;
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    public HomeView(LoanService loanService, PersonService personService){
        this.loanService = loanService;
        this.personService = personService;
        if (principal instanceof AppUser) {
            this.username = ((AppUser)principal).getUsername();
            this.appUser = ((AppUser) principal);
            System.out.println(username);
        }

        Label bankMoney = new Label("Amount: " + appUser.getBank().getMoney());

        add(bankMoney, parChart(), avgCreditScore(), avgAge(), moneyPerMonthChart(), refresh);
    }

    private Component parChart(){
        Chart chart = new Chart(ChartType.PIE);

        DataSeries dataSeries = new DataSeries();
        dataSeries.add(new DataSeriesItem("Accepted", personService.findAllAccepted(appUser.getBank(), Person.Status.ACCEPTED).size()));
        dataSeries.add(new DataSeriesItem("Pending", personService.findAllPending(appUser.getBank(), Person.Status.PENDING).size()));
        dataSeries.add(new DataSeriesItem("Rejected", personService.findAllRejected(appUser.getBank(), Person.Status.REJECTED).size()));

        chart.getConfiguration().setSeries(dataSeries);
        return chart;
    }

    private Component avgCreditScore(){
        List<Person> people = personService.findAllAccepted(appUser.getBank(), Person.Status.ACCEPTED);
        int total = people.size();
        double totalScore = 0.0;

        for(Person p : people){
            totalScore += p.getCreditScore();
        }

        return new Label("Average Credit Score: " + totalScore/total);
    }

    private Component avgAge(){
        List<Person> people = personService.findAllAccepted(appUser.getBank(), Person.Status.ACCEPTED);
        int total = people.size();
        double totalAge = 0.0;

        for(Person p : people){
            totalAge += p.getAge();
        }

        return new Label("Average Age: " + totalAge/total);
    }

    private Component moneyPerMonthChart(){
        Chart chart = new Chart(ChartType.LINE);
        ArrayList<Double> list = appUser.getBank().getMoneyPerMonth();
        DataSeries dataSeries = new DataSeries();
        for(int i = 0; i < list.size(); i++){
            dataSeries.add(new DataSeriesItem(i, list.get(i)));
        }
        chart.getConfiguration().setSeries(dataSeries);

        return chart;
    }

}
