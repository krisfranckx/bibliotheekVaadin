package com.example.Vaadin_REST_bib.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthSuccesHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        authentication.getAuthorities().forEach(authority->{
            try {
                if(authority.getAuthority().equals("ROLE_ADMIN")){
                    response.sendRedirect("/ll/admin/books");
                } else if (authority.getAuthority().equals("ROLE_USER")) {
                    response.sendRedirect("/ll/books");
                } else if (authority.getAuthority().equals("ROLE_LKADMIN")) {
                    response.sendRedirect("/lk/admin/books");
                }else if (authority.getAuthority().equals("ROLE_LK")) {
                    response.sendRedirect("/lk/books");
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        });
    }
}
