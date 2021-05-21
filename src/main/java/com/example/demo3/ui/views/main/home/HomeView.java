package com.example.demo3.ui.views.main.home;

import com.example.demo3.appuser.AppUser;
import com.example.demo3.loan.LoanService;
import com.example.demo3.person.Person;
import com.example.demo3.person.PersonService;
import com.example.demo3.ui.MainLayout;
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
    Chart chart = new Chart(ChartType.PIE);
    Chart chart2 = new Chart(ChartType.LINE);
    Label creditScore = new Label();
    Label age = new Label();
    Label bankMoney = new Label();


    public HomeView(LoanService loanService, PersonService personService){
        this.loanService = loanService;
        this.personService = personService;
        if (principal instanceof AppUser) {
            this.username = ((AppUser)principal).getUsername();
            this.appUser = ((AppUser) principal);
            System.out.println(username);
        }

        refresh.addClickListener(click -> resetAll());

        bankMoney = new Label("Amount: " + appUser.getBank().getMoney());

        avgAge();
        avgCreditScore();

        add(bankMoney, parChart(), moneyPerMonthChart(), creditScore, age, refresh);
    }

    private Component parChart(){
        DataSeries dataSeries = new DataSeries();
        dataSeries.add(new DataSeriesItem("Accepted", personService.findAllAccepted(appUser.getBank(), Person.Status.ACCEPTED).size()));
        dataSeries.add(new DataSeriesItem("Pending", personService.findAllPending(appUser.getBank(), Person.Status.PENDING).size()));
        dataSeries.add(new DataSeriesItem("Rejected", personService.findAllRejected(appUser.getBank(), Person.Status.REJECTED).size()));

        chart.getConfiguration().setSeries(dataSeries);
        return chart;
    }

    private void avgCreditScore(){
        List<Person> people = personService.findAllAccepted(appUser.getBank(), Person.Status.ACCEPTED);
        int total = people.size();
        double totalScore = 0.0;

        for(Person p : people){
            totalScore += p.getCreditScore();
        }

        creditScore.setText("Average Credit Score: " + totalScore/total);
    }

    private void avgAge(){
        List<Person> people = personService.findAllAccepted(appUser.getBank(), Person.Status.ACCEPTED);
        int total = people.size();
        double totalAge = 0.0;

        for(Person p : people){
            totalAge += p.getAge();
        }

        age.setText("Average Age: " + totalAge/total);
    }

    private Component moneyPerMonthChart(){

        ArrayList<Double> list = appUser.getBank().getMoneyPerMonth();
        DataSeries dataSeries = new DataSeries();
        for(int i = 0; i < list.size(); i++){
            dataSeries.add(new DataSeriesItem(i, list.get(i)));
        }
        chart2.getConfiguration().setSeries(dataSeries);

        return chart2;
    }

    private void resetAll(){
        DataSeries dataSeries = new DataSeries();
        dataSeries.add(new DataSeriesItem("Accepted", personService.findAllAccepted(appUser.getBank(), Person.Status.ACCEPTED).size()));
        dataSeries.add(new DataSeriesItem("Pending", personService.findAllPending(appUser.getBank(), Person.Status.PENDING).size()));
        dataSeries.add(new DataSeriesItem("Rejected", personService.findAllRejected(appUser.getBank(), Person.Status.REJECTED).size()));
        chart.getConfiguration().setSeries(dataSeries);

        ArrayList<Double> list = appUser.getBank().getMoneyPerMonth();
        DataSeries dataSeries2 = new DataSeries();
        for(int i = 0; i < list.size(); i++){
            dataSeries2.add(new DataSeriesItem(i, list.get(i)));
        }
        chart2.getConfiguration().setSeries(dataSeries2);

        List<Person> people = personService.findAllAccepted(appUser.getBank(), Person.Status.ACCEPTED);
        int total = people.size();
        double totalAge = 0.0;

        for(Person p : people){
            totalAge += p.getAge();
        }

        age.setText("Average Age: " + totalAge/total);

        double totalScore = 0.0;

        for(Person p : people){
            totalScore += p.getCreditScore();
        }

        creditScore.setText("Average Credit Score: " + totalScore/total);

        bankMoney = new Label("Amount: " + appUser.getBank().getMoney());
    }

}
