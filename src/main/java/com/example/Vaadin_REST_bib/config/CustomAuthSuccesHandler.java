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
        String target = authentication.getAuthorities().stream()
                .map(authority -> authority.getAuthority())
                .filter(authority -> authority.equals("ROLE_ADMIN")
                        || authority.equals("ROLE_USER")
                        || authority.equals("ROLE_LKADMIN")
                        || authority.equals("ROLE_LK"))
                .findFirst()
                .map(this::targetForAuthority)
                .orElse("/login?error");

        response.sendRedirect(target);
    }

    private String targetForAuthority(String authority) {
        return switch (authority) {
            case "ROLE_ADMIN" -> "/ll/admin/books";
            case "ROLE_USER" -> "/ll/books";
            case "ROLE_LKADMIN" -> "/lk/admin/books";
            case "ROLE_LK" -> "/lk/books";
            default -> "/login?error";
        };
    }
}
