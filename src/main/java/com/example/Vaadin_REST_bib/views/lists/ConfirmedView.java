package com.example.Vaadin_REST_bib.views.lists;


import com.example.Vaadin_REST_bib.views.login.LoginView;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@PageTitle("Bibliotheek | Registratie")
//@Route(value = "registration", layout = ADMIN_LL_MainLayout.class) //wordt geladen binnen mainlayout
@Route(value = "confirmed")
@AnonymousAllowed
public class ConfirmedView extends VerticalLayout {




    public ConfirmedView() {
        setSizeFull();  //tot beneden
        this.setAlignItems(Alignment.CENTER);
        setWidthFull();
        this.setAlignItems(Alignment.CENTER);
        this.setJustifyContentMode(JustifyContentMode.CENTER);
        Paragraph paragraph = new Paragraph("Je account werd bevestigd.");
        Paragraph paragraph2 = new Paragraph("Je kan nu aanmelden bij de bibliotheek.");

        RouterLink loginLink = new RouterLink("Login", LoginView.class);

        add(
            paragraph, paragraph2, loginLink
        );

    }
}
