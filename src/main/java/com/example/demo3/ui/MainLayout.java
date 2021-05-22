package com.example.demo3.ui;

import com.example.demo3.accounts.checkingaccount.CreditAccount;
import com.example.demo3.accounts.investingaccount.InvestingAccount;
import com.example.demo3.accounts.savingsaccount.SavingsAccount;
import com.example.demo3.appuser.AppUser;
import com.example.demo3.loan.Loan;
import com.example.demo3.loan.LoanService;
import com.example.demo3.person.Person;
import com.example.demo3.person.PersonService;
import com.example.demo3.ui.views.main.accounts.InvestingAccountsView;
import com.example.demo3.ui.views.main.advertisement.AdvertisementView;
import com.example.demo3.ui.views.main.customers.AcceptedCustomersView;
import com.example.demo3.ui.views.main.customers.CustomersView;
import com.example.demo3.ui.views.main.home.HomeView;
import com.example.demo3.ui.views.main.loans.AcceptedLoansView;
import com.example.demo3.ui.views.main.loans.LoansView;
import com.example.demo3.ui.views.main.stocks.StockTransactionsView;
import com.example.demo3.ui.views.main.stocks.StocksView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Router;
import com.vaadin.flow.router.RouterLink;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.demo3.ui.views.main.Template.*;

//App layout view
@CssImport("./styles/shared-styles.css")
public class MainLayout extends AppLayout {

    PersonService personService;
    LoanService loanService;
    Button openBank = new Button("Open Bank");
    Button refresh = new Button("Refresh");
    String username;
    AppUser appUser;
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
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

    public MainLayout(PersonService personService, LoanService loanService) {
        this.personService = personService;
        this.loanService = loanService;
        if (principal instanceof AppUser) {
            this.appUser = ((AppUser)principal);
            this.username = ((AppUser)principal).getUsername();
            createHeaderLoggedIn();
            createDrawerLoggedIn();
        }else{
            createHeaderNotLoggedIn();
        }
        refresh.addClickListener(click -> {
            resetNum();
        });

        resetNum();
        System.out.println("WLIUFHWEIFUHEIFOUGWEFIYGEIFYBWEIYFUBEFUYWEYIUFG");
    }

    //reset bankNum and change state of bank
    private void resetNum(){
        bankNum.setText("Amount: " + appUser.getBank().getMoney());
        openBank.addClickListener(click -> changeState());
        if(bankState){
            openBank.setText("Close Bank");
        }else{
            openBank.setText("Open Bank");
        }
    }

    //changes state of bank
    private void changeState(){
        if(bankState){
            bankState = false;
            openBank.setText("Open Bank");
            timer.cancel();
            timer = new Timer();
        }else{
            openBank.setText("Close Bank");
            bankState = true;
            timer.schedule(new MainLayout.addPerson(), 0, 5000);
        }
    }

    //create header for not logged in users
    private void createHeaderNotLoggedIn() {
        H1 logo = new H1("Bank");
        logo.addClassName("logo");

        Anchor login = new Anchor("/login", "Log in");
        Anchor signUp = new Anchor("/register", "Register");
        HorizontalLayout header = new HorizontalLayout(logo, login, signUp);
        header.addClassName("header");
        header.setWidth("100%");
        header.expand(logo);
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);

        addToNavbar(header);
    }

    //create header for logged in users
    private void createHeaderLoggedIn() {
        RouterLink bankTycoon = new RouterLink("Bank Tycoon", HomeView.class);
        H1 logo = new H1(this.username);
        logo.addClassName("logo");
        bankNum.setText("Amount: " + myRound(appUser.getBank().getMoney(), 2));
        bankNum.addClassName("logo");

        Anchor logout = new Anchor("/logout", "Log out");

        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), bankTycoon, logo, bankNum, openBank, refresh, logout);
        header.addClassName("header");
        header.setWidth("100%");
        header.expand(logo);
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);

        addToNavbar(header);
    }

    //create the side drawer
    private void createDrawerLoggedIn() {

        addToDrawer(new VerticalLayout(
                new RouterLink("Customers", CustomersView.class),
                new RouterLink("Accepted Customers", AcceptedCustomersView.class),
                new RouterLink("Loans", LoansView.class),
                new RouterLink("Accepted Loans", AcceptedLoansView.class),
                new RouterLink("Advertisement", AdvertisementView.class),
                new RouterLink("Stocks", StocksView.class),
                new RouterLink("Stock Transactions", StockTransactionsView.class),
                new RouterLink("Investing Accounts", InvestingAccountsView.class)
        ));
    }

    //rounding function
    private double myRound(double numToRound, int placeValue){
        numToRound = numToRound*(Math.pow(10, placeValue));
        numToRound = Math.round(numToRound);
        numToRound = numToRound/(Math.pow(10, placeValue));
        return numToRound;
    }

}
