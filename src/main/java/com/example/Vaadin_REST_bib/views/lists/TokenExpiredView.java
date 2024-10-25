package com.example.Vaadin_REST_bib.views.lists;


import com.example.Vaadin_REST_bib.views.login.LoginView;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@PageTitle("Bibliotheek | Registratie")
@Route(value = "expired")
@AnonymousAllowed
public class TokenExpiredView extends VerticalLayout {
    public TokenExpiredView() {
        setSizeFull();  //tot beneden
        this.setAlignItems(Alignment.CENTER);
        setWidthFull();
        this.setAlignItems(Alignment.CENTER);
        this.setJustifyContentMode(JustifyContentMode.CENTER);
        H3 h3 = new H3("Er liep iets mis bij de bevestiging.");
        Paragraph paragraph = new Paragraph("De link voor bevestiging is vervallen (na 15 minuten) of je account werd reeds bevestigd.");
        Paragraph paragraph2 = new Paragraph("Er werd een nieuwe link gestuurd om je adres alsnog te bevestigen");

        RouterLink loginLink = new RouterLink("Login", LoginView.class);

        add(
            h3,paragraph, paragraph2
        );

    }
}
