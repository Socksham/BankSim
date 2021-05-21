package com.example.demo3.ui.views.main.home;

import com.example.demo3.appuser.AppUser;
import com.example.demo3.loan.Loan;
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
    Chart chart3 = new Chart(ChartType.PIE);
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
        parChart();
        moneyPerMonthChart();
        loanParChart();

        Div pieCharts = new Div(chart, chart3);
        pieCharts.addClassName("content");
        pieCharts.setWidthFull();

        add(bankMoney, pieCharts, chart2, creditScore, age, refresh);
    }

    private void parChart(){
        DataSeries dataSeries = new DataSeries();
        dataSeries.add(new DataSeriesItem("Accepted", personService.findAllAccepted(appUser.getBank(), Person.Status.ACCEPTED).size()));
        dataSeries.add(new DataSeriesItem("Pending", personService.findAllPending(appUser.getBank(), Person.Status.PENDING).size()));
        dataSeries.add(new DataSeriesItem("Rejected", personService.findAllRejected(appUser.getBank(), Person.Status.REJECTED).size()));
        chart.setSizeFull();
        chart.getConfiguration().setSeries(dataSeries);
    }

    private void loanParChart(){
        DataSeries dataSeries2 = new DataSeries();
        dataSeries2.add(new DataSeriesItem("Accepted", loanService.findAllAccepted(appUser.getBank(), Loan.Status.ACCEPTED).size()));
        dataSeries2.add(new DataSeriesItem("Pending", loanService.findAllPending(appUser.getBank(), Loan.Status.PENDING).size()));
        dataSeries2.add(new DataSeriesItem("Rejected", loanService.findAllRejected(appUser.getBank(), Loan.Status.REJECTED).size()));
        chart3.setSizeFull();
        chart3.getConfiguration().setSeries(dataSeries2);
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

    private void moneyPerMonthChart(){

        ArrayList<Double> list = appUser.getBank().getMoneyPerMonth();
        DataSeries dataSeries = new DataSeries();
        for(int i = 0; i < list.size(); i++){
            dataSeries.add(new DataSeriesItem(i, list.get(i)));
        }
        chart2.getConfiguration().setSeries(dataSeries);
    }

    private void resetAll(){
        DataSeries dataSeries = new DataSeries();
        dataSeries.add(new DataSeriesItem("Accepted", personService.findAllAccepted(appUser.getBank(), Person.Status.ACCEPTED).size()));
        dataSeries.add(new DataSeriesItem("Pending", personService.findAllPending(appUser.getBank(), Person.Status.PENDING).size()));
        dataSeries.add(new DataSeriesItem("Rejected", personService.findAllRejected(appUser.getBank(), Person.Status.REJECTED).size()));
        chart.getConfiguration().setSeries(dataSeries);

        DataSeries dataSeries3 = new DataSeries();
        dataSeries3.add(new DataSeriesItem("Accepted", loanService.findAllAccepted(appUser.getBank(), Loan.Status.ACCEPTED).size()));
        dataSeries3.add(new DataSeriesItem("Pending", loanService.findAllPending(appUser.getBank(), Loan.Status.PENDING).size()));
        dataSeries3.add(new DataSeriesItem("Rejected", loanService.findAllRejected(appUser.getBank(), Loan.Status.REJECTED).size()));

        chart3.getConfiguration().setSeries(dataSeries3);

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

        chart.drawChart(true);
        chart2.drawChart(true);
        chart3.drawChart(true);

        creditScore.setText("Average Credit Score: " + totalScore/total);

        bankMoney = new Label("Amount: " + appUser.getBank().getMoney());
    }

}
