package com.example.Vaadin_REST_bib.views.lists;


import com.example.Vaadin_REST_bib.config.Settings;
import com.example.Vaadin_REST_bib.restClasses.Response;
import com.example.Vaadin_REST_bib.services.ConfirmationService;
import com.example.Vaadin_REST_bib.views.login.LoginView;
import com.fasterxml.jackson.databind.introspect.TypeResolutionContext;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Future;

@PageTitle("Bibliotheek | Registratie")
//@Route(value = "registration", layout = ADMIN_LL_MainLayout.class) //wordt geladen binnen mainlayout
@Route(value = "confirm")
@AnonymousAllowed
public class ConfirmView extends VerticalLayout implements BeforeEnterObserver {
    private String token;
    private String userclick;
    public final ConfirmationService confirmationService;


    public ConfirmView(ConfirmationService confirmationService) {
        this.confirmationService = confirmationService;
        setSizeFull();  //tot beneden
        this.setAlignItems(Alignment.CENTER);
        setWidthFull();
        this.setAlignItems(Alignment.CENTER);
        this.setJustifyContentMode(JustifyContentMode.CENTER);
        Paragraph paragraph = new Paragraph("Klik op onderstaande link om je bevestiging te voltooien.");

        //RouterLink confirmlink = new RouterLink("Klik om je bevestiging te voltooien", ConfirmedView.class);
        Button button = new Button("Klik hier");
        button.addClickListener(event -> {
            System.out.println("Geklikt°°°°°°°°°°°°°°°°°°°°°");
            confirmationService.confirmWithUserclick(token,"true");
            UI.getCurrent().navigate(Settings.BASE_URL_FRONT+"/confirmed");
        });
        add(paragraph,button);






    }


    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        Map<String, List<String>> parameters = beforeEnterEvent.getLocation().getQueryParameters().getParameters();
        token = parameters.get("token").get(0);
        System.out.println(token);
        if (parameters.size()>1){
            userclick = parameters.get("userclick").get(0);
            System.out.println(userclick);
        }

    }
}
