package com.example.demo3.registration;

import com.example.demo3.appuser.AppUser;
import com.example.demo3.appuser.AppUserRole;
import com.example.demo3.appuser.AppUserService;
import com.example.demo3.bank.Bank;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

//service for registering users. from vaadin tutorial series
@Service
@AllArgsConstructor
public class RegistrationService {

    private final AppUserService appUserService;
    private final EmailValidator emailValidator;

    public String register(RegistrationRequest request){
        boolean isValidEmail = emailValidator.test(request.getEmail());
        if(!isValidEmail){
            throw new IllegalStateException("email not valid");
        }
        return appUserService.signUpUser(
                new AppUser(
                        request.getFirstName(),
                        request.getLastName(),
                        request.getEmail(),
                        request.getPassword(),
                        AppUserRole.USER
                )
        );
    }
}
