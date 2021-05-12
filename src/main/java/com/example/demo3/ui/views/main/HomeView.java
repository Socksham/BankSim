package com.example.demo3.ui.views.main;

import com.example.demo3.appuser.AppUser;
import com.example.demo3.appuser.AppUserService;
import com.example.demo3.ui.MainLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.security.core.context.SecurityContextHolder;

@Route(value = "", layout = MainLayout.class)
@PageTitle("Home | Banking")
public class HomeView extends VerticalLayout {

    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    public HomeView(){
        String username;
        if (principal instanceof AppUser) {
            username = ((AppUser)principal).getUsername();
            System.out.println(username);
        } else {
            username = principal.toString();
            System.out.println(username);
        }
        add(
                new H1(username)
        );
    }
}
