package com.example.demo3.ui.views.registration;

import com.example.demo3.appuser.AppUser;
import com.example.demo3.appuser.AppUserService;
import com.example.demo3.registration.RegistrationRequest;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;


@Route(value = "/register")
@PageTitle("Register | Banking")
public class RegistrationView extends VerticalLayout {
    private final AppUserService appUserService;

    TextField firstName = new TextField("First name");
    TextField lastName = new TextField("Last name");
    EmailField email = new EmailField("Email");
    TextField password = new TextField("Password");
    Button register = new Button("Register");

    public RegistrationView(AppUserService appUserService) {
        this.appUserService = appUserService;
        setSizeFull();

        register.addClickListener(click -> saveUser());

        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);
        add(
                new H1("REGISTER"),
                firstName,
                lastName,
                email,
                password,
                register
        );
    }

    //save user
    private void saveUser(){
        if(!appUserService.signUpUser(new AppUser(firstName.getValue(), lastName.getValue(), email.getValue(), password.getValue())).equals("ERROR")){
            UI.getCurrent().navigate("/login");
        }
    }

}
