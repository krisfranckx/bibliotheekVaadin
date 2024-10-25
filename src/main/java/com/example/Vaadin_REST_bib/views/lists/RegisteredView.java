package com.example.Vaadin_REST_bib.views.lists;


import com.example.Vaadin_REST_bib.services.UserService;
import com.example.Vaadin_REST_bib.views.forms.RegistrationForm;
import com.example.Vaadin_REST_bib.views.forms.UserForm_ChangePasword;
import com.example.Vaadin_REST_bib.views.login.LoginView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@PageTitle("Bibliotheek | Registratie")
//@Route(value = "registration", layout = ADMIN_LL_MainLayout.class) //wordt geladen binnen mainlayout
@Route(value = "registered")
@AnonymousAllowed
public class RegisteredView extends VerticalLayout {




    public RegisteredView() {
        setSizeFull();  //tot beneden
        this.setAlignItems(Alignment.CENTER);
        setWidthFull();
        this.setAlignItems(Alignment.CENTER);
        this.setJustifyContentMode(JustifyContentMode.CENTER);
        Paragraph paragraph = new Paragraph("Je bent geregistreerd bij de bibliotheek.");
        Paragraph paragraph2 = new Paragraph("Ga naar je inbox om je account te bevestigen en log nadien in.");

        RouterLink loginLink = new RouterLink("Login", LoginView.class);

        add(
            paragraph, paragraph2, loginLink
        );

    }
}
