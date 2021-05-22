package com.example.demo3.ui.views.main.advertisement;

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
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.*;

//advertisements view
@Component
@Scope("prototype")
@Route(value = "/advertisement", layout = MainLayout.class)
@PageTitle("Bank | Advertisement")
public class AdvertisementView extends Template {
    PersonService personService;
    LoanService loanService;
    String username;
    AppUser appUser;
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    Button buyTvAd = new Button("Buy");
    Button buyRadioAd = new Button("Buy");
    Button buyBillboardAd = new Button("Buy");
    Button buyNewspaperAd = new Button("Buy");

    Div billBoardAd;
    Div tvAd;
    Div newsPaperAd;
    Div radioAd;

    String[] firsts = {"Liam", "Noah", "Oliver", "Elijah", "William", "James", "Benjamin", "Lucas", "Henry","Alexander",
            "Mason", "Michael", "Ethan", "Daniel", "Jacob", "Logan", "Jackson", "Levi", "Sebastian", "Mateo", "Jack",
            "Owen", "Theodore", "Aiden", "Samuel", "Joseph", "John", "David", "Wyatt", "Matthew", "Luke", "Asher",
            "Carter", "Julian"};
    String[] lasts = {"Smith", "Johnson", "Williams", "Brown", "Jones", "Garcia", "Miller", "Davis"};

    int months = 0;

    //class that runs function every blank seconds(depends on the time set for the bank)
    public class addPerson extends TimerTask {
        public void run() {
            months++;
            CreditAccount creditAccount;
            SavingsAccount savingsAccount;
            InvestingAccount investingAccount = null;
            Random rand = new Random();
            //create a person
            Person personToAdd = new Person(appUser.getBank(), firsts[(rand.nextInt(firsts.length))], lasts[(rand.nextInt(lasts.length))],
                    (int)(Math.random() * ((850 - 300) + 1))+300, (int)(Math.random() * ((65 - 18) + 1))+18);

            //add accounts based on age and credit score
            if(personToAdd.getAge() < 24){
                if(personToAdd.getCreditScore() < 630){
                    creditAccount = new CreditAccount(personToAdd, 500.0 + (1000.0 - 500.0) * rand.nextDouble());
                    savingsAccount = new SavingsAccount(personToAdd, 5000.0 + (10000.0 - 5000.0) * rand.nextDouble());
                    creditAccount.setAmountOfMoney(myRound(creditAccount.getAmountOfMoney(), 2));
                    savingsAccount.setAmountOfMoney(myRound(savingsAccount.getAmountOfMoney(), 2));
                }else if(personToAdd.getCreditScore() < 690){
                    creditAccount = new CreditAccount(personToAdd, 700.0 + (1200.0 - 700.0) * rand.nextDouble());
                    savingsAccount = new SavingsAccount(personToAdd, 7000.0 + (12000.0 - 7000.0) * rand.nextDouble());
                    creditAccount.setAmountOfMoney(myRound(creditAccount.getAmountOfMoney(), 2));
                    savingsAccount.setAmountOfMoney(myRound(savingsAccount.getAmountOfMoney(), 2));
                }else if(personToAdd.getCreditScore() < 720){
                    creditAccount = new CreditAccount(personToAdd, 900.0 + (1400.0 - 900.0) * rand.nextDouble());
                    savingsAccount = new SavingsAccount(personToAdd, 9000.0 + (14000.0 - 9000.0) * rand.nextDouble());
                    creditAccount.setAmountOfMoney(myRound(creditAccount.getAmountOfMoney(), 2));
                    savingsAccount.setAmountOfMoney(myRound(savingsAccount.getAmountOfMoney(), 2));
                }else{
                    creditAccount = new CreditAccount(personToAdd, 1200.0 + (1600.0 - 1200.0) * rand.nextDouble());
                    savingsAccount = new SavingsAccount(personToAdd, 12000.0 + (16000.0 - 12000.0) * rand.nextDouble());
                    creditAccount.setAmountOfMoney(myRound(creditAccount.getAmountOfMoney(), 2));
                    savingsAccount.setAmountOfMoney(myRound(savingsAccount.getAmountOfMoney(), 2));
                }
            }else if(personToAdd.getAge() < 45){
                if(personToAdd.getCreditScore() < 630){
                    creditAccount = new CreditAccount(personToAdd, 500.0 + (1000.0 - 500.0) * rand.nextDouble());
                    savingsAccount = new SavingsAccount(personToAdd, 5000.0 + (10000.0 - 5000.0) * rand.nextDouble());
                    creditAccount.setAmountOfMoney(myRound(creditAccount.getAmountOfMoney(), 2));
                    savingsAccount.setAmountOfMoney(myRound(savingsAccount.getAmountOfMoney(), 2));
                }else if(personToAdd.getCreditScore() < 690){
                    creditAccount = new CreditAccount(personToAdd, 900.0 + (1200.0 - 900.0) * rand.nextDouble());
                    savingsAccount = new SavingsAccount(personToAdd, 9000.0 + (12000.0 - 9000.0) * rand.nextDouble());
                    creditAccount.setAmountOfMoney(myRound(creditAccount.getAmountOfMoney(), 2));
                    savingsAccount.setAmountOfMoney(myRound(savingsAccount.getAmountOfMoney(), 2));
                }else if(personToAdd.getCreditScore() < 720){
                    creditAccount = new CreditAccount(personToAdd, 2400.0 + (3500.0 - 2400.0) * rand.nextDouble());
                    savingsAccount = new SavingsAccount(personToAdd, 24000.0 + (35000.0 - 24000.0) * rand.nextDouble());
                    investingAccount = new InvestingAccount(personToAdd, 12000.0 + (16000.0 - 12000.0) * rand.nextDouble());
                    creditAccount.setAmountOfMoney(myRound(creditAccount.getAmountOfMoney(), 2));
                    savingsAccount.setAmountOfMoney(myRound(savingsAccount.getAmountOfMoney(), 2));
                    investingAccount.setAmountOfMoney(myRound(investingAccount.getAmountOfMoney(), 2));
                }else{
                    creditAccount = new CreditAccount(personToAdd, 4000.0 + (5500.0 - 4000.0) * rand.nextDouble());
                    savingsAccount = new SavingsAccount(personToAdd, 40000.0 + (55000.0 - 40000.0) * rand.nextDouble());
                    investingAccount = new InvestingAccount(personToAdd, 20000.0 + (45000.0 - 20000.0) * rand.nextDouble());
                    creditAccount.setAmountOfMoney(myRound(creditAccount.getAmountOfMoney(), 2));
                    savingsAccount.setAmountOfMoney(myRound(savingsAccount.getAmountOfMoney(), 2));
                    investingAccount.setAmountOfMoney(myRound(investingAccount.getAmountOfMoney(), 2));
                }
            }else{
                if(personToAdd.getCreditScore() < 630){
                    creditAccount = new CreditAccount(personToAdd, 500.0 + (1000.0 - 500.0) * rand.nextDouble());
                    savingsAccount = new SavingsAccount(personToAdd, 5000.0 + (10000.0 - 5000.0) * rand.nextDouble());
                    creditAccount.setAmountOfMoney(myRound(creditAccount.getAmountOfMoney(), 2));
                    savingsAccount.setAmountOfMoney(myRound(savingsAccount.getAmountOfMoney(), 2));
                }else if(personToAdd.getCreditScore() < 690){
                    creditAccount = new CreditAccount(personToAdd, 800.0 + (1100.0 - 800.0) * rand.nextDouble());
                    savingsAccount = new SavingsAccount(personToAdd, 8000.0 + (11000.0 - 8000.0) * rand.nextDouble());
                    creditAccount.setAmountOfMoney(myRound(creditAccount.getAmountOfMoney(), 2));
                    savingsAccount.setAmountOfMoney(myRound(savingsAccount.getAmountOfMoney(), 2));
                }else if(personToAdd.getCreditScore() < 720){
                    creditAccount = new CreditAccount(personToAdd, 2500.0 + (3400.0 - 2500.0) * rand.nextDouble());
                    savingsAccount = new SavingsAccount(personToAdd, 25000.0 + (34000.0 - 25000.0) * rand.nextDouble());
                    investingAccount = new InvestingAccount(personToAdd, 13000.0 + (17000.0 - 13000.0) * rand.nextDouble());
                    creditAccount.setAmountOfMoney(myRound(creditAccount.getAmountOfMoney(), 2));
                    savingsAccount.setAmountOfMoney(myRound(savingsAccount.getAmountOfMoney(), 2));
                    investingAccount.setAmountOfMoney(myRound(investingAccount.getAmountOfMoney(), 2));
                }else{
                    creditAccount = new CreditAccount(personToAdd, 4500.0 + (6500.0 - 4500.0) * rand.nextDouble());
                    savingsAccount = new SavingsAccount(personToAdd, 45000.0 + (65000.0 - 45000.0) * rand.nextDouble());
                    investingAccount = new InvestingAccount(personToAdd, 23000.0 + (50000.0 - 23000.0) * rand.nextDouble());
                    creditAccount.setAmountOfMoney(myRound(creditAccount.getAmountOfMoney(), 2));
                    savingsAccount.setAmountOfMoney(myRound(savingsAccount.getAmountOfMoney(), 2));
                    investingAccount.setAmountOfMoney(myRound(investingAccount.getAmountOfMoney(), 2));
                }
            }
            //set all accounts
            personToAdd.setCreditAccount(creditAccount);
            personToAdd.setSavingsAccount(savingsAccount);
            if(null != investingAccount){
                System.out.println("WIEUGFEIOUGWIYSGFWIEKYGFWGEIY");
                personToAdd.setInvestingAccount(investingAccount);
            }

            //get all accepted players and create loans
            int n = personService.findAllAccepted(appUser.getBank(), Person.Status.ACCEPTED).size();
            List<Person> peopleAccepted = personService.findAllAccepted(appUser.getBank(), Person.Status.ACCEPTED);

            personService.save(personToAdd);

            if(months%12 == 0){
                for(Person p : peopleAccepted){
                    p.setAge(p.getAge() + 1);
                    if(p.getAge() > 75){
                        p.setStatus(Person.Status.BANKRUPT);
                    }
                    personService.save(p);
                }
            }
            if(n > 0){
                Person p = peopleAccepted.get(rand.nextInt(peopleAccepted.size()));
                Loan loan = new Loan(p, myRound(1000.0 + (100000.0 - 1000.0) * rand.nextDouble(), 2), appUser.getBank(), (int)(Math.random() * ((30 - 3) + 1))+3);
                loanService.save(loan);
            }

            //add monthly payments for each accepted loan
            List<Loan> loansAccepted = loanService.findAllAccepted(appUser.getBank(), Loan.Status.ACCEPTED);
            for(Loan loan : loansAccepted){
                if(loan.getPerson() != personToAdd){
                    if(loan.getPerson().getLoans().size() < 3){
                        appUser.getBank().setMoney(myRound(appUser.getBank().getMoney() + loan.getMonthlyPayment(), 2));
                        loan.setYearsToPay(loan.getYearsToPay() - 1);
                    }
                }
                if(loan.getYearsToPay() <= 0){
                    loan.setLoanRole(Loan.Status.PAID);
                }
            }

            //add and remove money based on changes in checking and savings accounts
            for(Person person : peopleAccepted){
                if (!peopleAcceptedRecently.contains(person)) {
                    System.out.println("WIGYFWOFGIYWOIGUFWEIYFGIOYGWEFYOGWUEFIUFY");
                    appUser.getBank().setMoney(appUser.getBank().getMoney() - person.getCreditAccount().getAmountOfMoney() - person.getSavingsAccount().getAmountOfMoney());

                    if (person.getCreditScore() < 630) {
                        person.getCreditAccount().setAmountOfMoney(person.getCreditAccount().getAmountOfMoney() - 288.0);
                        person.getCreditAccount().setAmountOfMoney(myRound(person.getCreditAccount().getAmountOfMoney() + 40.0 + (300.0 - 40.0) * rand.nextDouble(), 2));
                        if (person.getCreditAccount().getAmountOfMoney() < 0) {
                            person.getCreditAccount().setAmountOfMoney(person.getCreditAccount().getAmountOfMoney() - 30.0);
                        }
                        if (person.getCreditAccount().getAmountOfMoney() < 100.0) {
                            person.setStatus(Person.Status.BANKRUPT);
                        }
                    } else if (person.getCreditScore() < 690) {
                        person.getCreditAccount().setAmountOfMoney(person.getCreditAccount().getAmountOfMoney() - 328.0);
                        person.getCreditAccount().setAmountOfMoney(myRound(person.getCreditAccount().getAmountOfMoney() + 300.0 + (600.0 - 300.0) * rand.nextDouble(), 2));
                        if (person.getCreditAccount().getAmountOfMoney() < 0) {
                            person.getCreditAccount().setAmountOfMoney(person.getCreditAccount().getAmountOfMoney() - 30.0);
                        }
                        if (person.getCreditAccount().getAmountOfMoney() < 100.0) {
                            person.setStatus(Person.Status.BANKRUPT);
                        }
                    } else if (person.getCreditScore() < 720) {
                        person.getCreditAccount().setAmountOfMoney(person.getCreditAccount().getAmountOfMoney() - 358.0);
                        person.getCreditAccount().setAmountOfMoney(myRound(person.getCreditAccount().getAmountOfMoney() + 358.0 + (650.0 - 358.0) * rand.nextDouble(), 2));
                        if (person.getCreditAccount().getAmountOfMoney() < 0) {
                            person.getCreditAccount().setAmountOfMoney(person.getCreditAccount().getAmountOfMoney() - 30.0);
                        }
                        if (person.getCreditAccount().getAmountOfMoney() < 100.0) {
                            person.setStatus(Person.Status.BANKRUPT);
                        }
                    } else {
                        person.getCreditAccount().setAmountOfMoney(person.getCreditAccount().getAmountOfMoney() - 428.0);
                        person.getCreditAccount().setAmountOfMoney(myRound(person.getCreditAccount().getAmountOfMoney() + 500.0 + (700.0 - 500.0) * rand.nextDouble(), 2));
                        if (person.getCreditAccount().getAmountOfMoney() < 0) {
                            person.getCreditAccount().setAmountOfMoney(person.getCreditAccount().getAmountOfMoney() - 30.0);
                        }
                        if (person.getCreditAccount().getAmountOfMoney() < 100.0) {
                            person.setStatus(Person.Status.BANKRUPT);
                        }
                    }
                }
                appUser.getBank().setMoney(appUser.getBank().getMoney() + person.getCreditAccount().getAmountOfMoney() + person.getSavingsAccount().getAmountOfMoney());

            }

            //clear all recently added people
            peopleAcceptedRecently.clear();
            System.out.println("##00 " + appUser.getBank().getMoney());
            appUser.getBank().addToMoneyStat(appUser.getBank().getMoney());
            System.out.println("TIMER");
        }
        //rounding function
        private double myRound(double numToRound, int placeValue){
            numToRound = numToRound*(Math.pow(10,placeValue));
            numToRound = Math.round(numToRound);
            numToRound = numToRound/(Math.pow(10,placeValue));
            return numToRound;
        }
    }

    public AdvertisementView(PersonService personService, LoanService loanService){
        this.loanService = loanService;
        this.personService = personService;

        if(principal instanceof AppUser){
            this.appUser = ((AppUser) principal);
            this.username = appUser.getUsername();
        }

        buyBillboardAd.addClickListener(buttonClickEvent -> buyBillboardAd());
        billBoardAd = new Div(new Label("Billboard Ad"), new Label("Price: $100000"), buyBillboardAd);
        billBoardAd.addClassName("ad");

        buyNewspaperAd.addClickListener(buttonClickEvent -> buyNewspaperAd());
        newsPaperAd = new Div(new Label("Newspaper Ad"), new Label("Price: $10000"), buyNewspaperAd);
        newsPaperAd.addClassName("ad");

        buyRadioAd.addClickListener(buttonClickEvent -> buyRadioAd());
        radioAd = new Div(new Label("Radio Ad"), new Label("Price: $30000"), buyRadioAd);
        radioAd.addClassName("ad");

        buyTvAd.addClickListener(buttonClickEvent -> buyTvAd());
        tvAd = new Div(new Label("Tv Ad"), new Label("Price: $50000"), buyTvAd);
        tvAd.addClassName("ad");

        resetButtons();

        Div content = new Div(newsPaperAd, radioAd, tvAd, billBoardAd);
        content.setSizeFull();
        content.addClassName("adContent");

        add(content);
    }

    //function for buttons that call the add person function more frequently
    public void buyRadioAd(){
        secondsPerCustomer = 4000;
        if(appUser.getBank().getMoney() - 30000 > 0){
            appUser.getBank().setMoney(appUser.getBank().getMoney() - 30000);
            timer.cancel();
            timer = new Timer();
            timer.schedule(new AdvertisementView.addPerson(), 0, secondsPerCustomer);
            buyRadioAdButton = true;
        }
        resetButtons();
    }

    public void buyTvAd(){
        secondsPerCustomer = 3000;
        if(appUser.getBank().getMoney() - 50000 > 0){
            appUser.getBank().setMoney(appUser.getBank().getMoney() - 50000);
            timer.cancel();
            timer = new Timer();
            timer.schedule(new AdvertisementView.addPerson(), 0, secondsPerCustomer);
            buyTvAdButton = true;
        }
        resetButtons();
    }

    public void buyNewspaperAd(){
        secondsPerCustomer = 4500;
        if(appUser.getBank().getMoney() - 10000 > 0){
            appUser.getBank().setMoney(appUser.getBank().getMoney() - 10000);
            timer.cancel();
            timer = new Timer();
            timer.schedule(new AdvertisementView.addPerson(), 0, secondsPerCustomer);
            buyNewspaperAdButton = true;
        }
        resetButtons();
    }

    public void buyBillboardAd(){
        secondsPerCustomer = 2000;
        if(appUser.getBank().getMoney() - 100000 > 0){
            appUser.getBank().setMoney(appUser.getBank().getMoney() - 100000);
            timer.cancel();
            timer = new Timer();
            timer.schedule(new AdvertisementView.addPerson(), 0, secondsPerCustomer);
            buyBillboardAdButton = true;
        }
        resetButtons();
    }

    //reset everything
    public void resetButtons(){
        bankState = true;
        if(buyBillboardAdButton){
            buyBillboardAd.setEnabled(false);
        }

        if(buyNewspaperAdButton){
            buyNewspaperAd.setEnabled(false);
        }

        if(buyRadioAdButton){
            buyRadioAd.setEnabled(false);
        }

        if(buyTvAdButton){
            buyTvAd.setEnabled(false);
        }
    }
}

