package com.example.Vaadin_REST_bib.views;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Landing route for "/": after login Vaadin may redirect to the originally requested
 * page ("/"), so send the user to the start page that matches their role.
 */
@Route("")
@PageTitle("Bibliotheek GO! atheneum Boom")
@PermitAll
public class RootView extends Div implements BeforeEnterObserver {

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Set<String> roles = authentication == null ? Set.of() : authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        if (roles.contains("ROLE_ADMIN")) {
            event.forwardTo("ll/admin/books");
        } else if (roles.contains("ROLE_LKADMIN")) {
            event.forwardTo("lk/admin/books");
        } else if (roles.contains("ROLE_LK")) {
            event.forwardTo("lk/books");
        } else if (roles.contains("ROLE_USER")) {
            event.forwardTo("ll/books");
        } else {
            event.forwardTo("login");
        }
    }
}
