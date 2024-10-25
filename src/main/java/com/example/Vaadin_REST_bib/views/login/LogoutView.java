package com.example.Vaadin_REST_bib.views.login;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route("logout")
@PageTitle("Logout | GO! atheneum Boom")
@AnonymousAllowed
public class LogoutView extends VerticalLayout {

    public LogoutView() {
        addClassName("logout-view");
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        add(
                new H1("Je bent uitglogd bij Bibliotheek GO! atheneum Boom")
        );
    }

}
