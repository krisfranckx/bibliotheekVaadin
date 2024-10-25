package com.example.Vaadin_REST_bib.config;


import com.example.Vaadin_REST_bib.restClasses.User;
import com.example.Vaadin_REST_bib.services.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
   UserService userService;

    public UserDetailsServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userService
                .getUserByEmail(email)
                .getData()
                .getsecurityUser()
                .map(CustomSecurityUser::new).orElseThrow(()-> new UsernameNotFoundException("Username not found"+email));
    }



    /*public String signUpUser(User user){
        boolean userExists = userRepo.findByEmail(user.getEmail()).isPresent() ;
        if(userExists){
            throw new IllegalStateException("Email al in gebruik");
        }
        userService.createUser_registrated(user);


        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                user
        );
        confirmationTokenService.saveConfirmationToken(confirmationToken);
        emailService.sendHtmlEmail_confirmRegistration(user.getEmail(), user, confirmationToken.getToken());
        return token;
    }*/

    /*public void enableUser(String email) {
        User user = userRepo.findByEmail(email).get();
        user.setEnabled(true);
        user.setEnabledUI(true);
        userRepo.save(user);
    }*/

    /*public void sendNewConfirmationMail(User user){
        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                user
        );
        confirmationTokenService.saveConfirmationToken(confirmationToken);
        emailService.sendHtmlEmail_confirmRegistration(user.getEmail(), user, confirmationToken.getToken());
    }*/

}
