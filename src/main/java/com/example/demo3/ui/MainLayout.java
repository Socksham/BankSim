package com.example.demo3.ui;

import com.example.demo3.appuser.AppUser;
import com.example.demo3.loan.Loan;
import com.example.demo3.loan.LoanService;
import com.example.demo3.person.Person;
import com.example.demo3.person.PersonService;
import com.example.demo3.ui.views.main.customers.AcceptedCustomersView;
import com.example.demo3.ui.views.main.customers.CustomersView;
import com.example.demo3.ui.views.main.loans.AcceptedLoansView;
import com.example.demo3.ui.views.main.loans.LoansView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLink;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.demo3.ui.views.main.Template.bankState;
import static com.example.demo3.ui.views.main.Template.timer;

@CssImport("./styles/shared-styles.css")
public class MainLayout extends AppLayout {

    PersonService personService;
    LoanService loanService;
    Button openBank = new Button("Open Bank");
    String username;
    AppUser appUser;
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    String[] firsts = {"Liam",
            "Noah",
            "Oliver",
            "Elijah",
            "William",
            "James",
            "Benjamin",
            "Lucas",
            "Henry",
            "Alexander",
            "Mason",
            "Michael",
            "Ethan",
            "Daniel",
            "Jacob",
            "Logan",
            "Jackson",
            "Levi",
            "Sebastian",
            "Mateo",
            "Jack",
            "Owen",
            "Theodore",
            "Aiden",
            "Samuel",
            "Joseph",
            "John",
            "David",
            "Wyatt",
            "Matthew",
            "Luke",
            "Asher",
            "Carter",
            "Julian"};
    String[] lasts = {"Smith",
            "Johnson",
            "Williams",
            "Brown",
            "Jones",
            "Garcia",
            "Miller",
            "Davis"};

    class addPerson extends TimerTask {
        public void run() {
            int n = personService.findAllAccepted(appUser.getBank(), Person.Status.ACCEPTED).size();
            Random rand = new Random();
            List<Person> m = personService.findAllAccepted(appUser.getBank(), Person.Status.ACCEPTED);
            personService.save(new Person(appUser.getBank(), firsts[(rand.nextInt(firsts.length))], lasts[(rand.nextInt(lasts.length))], (int)(Math.random() * ((850 - 300) + 1))+300, (int)(Math.random() * ((65 - 18) + 1))+18));
            if(n > 0){
                Person p = m.get(rand.nextInt(m.size()));
                Loan loan = new Loan(p, 1000.0 + (100000.0 - 1000.0) * rand.nextDouble(), appUser.getBank(), 3 + (30 - 3) * rand.nextInt());
                loanService.save(loan);
            }
            System.out.println(personService.findAllPending(appUser.getBank(), Person.Status.PENDING));
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
        openBank.addClickListener(click -> {
            changeState();
        });
        if(bankState){
            openBank.setText("Close Bank");
        }else{
            openBank.setText("Open Bank");
        }
    }

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

    private void createHeaderLoggedIn() {
        H1 logo = new H1(this.username);
        logo.addClassName("logo");

        Anchor logout = new Anchor("/logout", "Log out");

        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), logo, openBank, logout);
        header.addClassName("header");
        header.setWidth("100%");
        header.expand(logo);
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);

        addToNavbar(header);
    }

    private void createDrawerLoggedIn() {

        addToDrawer(new VerticalLayout(
                new RouterLink("Customers", CustomersView.class),
                new RouterLink("Accepted Customers", AcceptedCustomersView.class),
                new RouterLink("Loans", LoansView.class),
                new RouterLink("Accepted Loans", AcceptedLoansView.class)
        ));
    }
}
