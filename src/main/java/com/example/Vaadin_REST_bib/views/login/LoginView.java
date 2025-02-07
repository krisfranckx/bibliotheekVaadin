package com.example.Vaadin_REST_bib.views.login;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.AppShellSettings;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.server.auth.AnonymousAllowed;


@Route("login")
@PageTitle("Login | GO! atheneum Boom")
@AnonymousAllowed
public class LoginView extends VerticalLayout implements BeforeEnterObserver {
    LoginForm toAddLoginForm = new LoginForm();
    Image logo = new Image("src/main/resources/static/images/img.png", "Logo GO! atheneum Boom");


    public LoginView() {
        addClassName("login-view");
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setWidthFull();
        setJustifyContentMode(JustifyContentMode.CENTER);

        LoginI18n i18n = LoginI18n.createDefault();
        LoginI18n.Form loginForm = i18n.getForm();
        i18n.setForm(loginForm);
        i18n.setAdditionalInformation("Indien je jouw wachtwoord vergeten bent of problemen ondervindt bij het inloggen, neem je contact op met je leerkracht.");
        loginForm.setTitle("Bibliotheek GO! atheneum Boom");
        loginForm.setPassword("Paswoord");
        loginForm.setUsername("Email");
        loginForm.setForgotPassword("Registreren");
        toAddLoginForm.addForgotPasswordListener(e->{
            System.out.println("Forgotpassword geklikt");
            UI.getCurrent().navigate("registration");
        } );
        LoginI18n.ErrorMessage errorMessage= i18n.getErrorMessage();
        errorMessage.setTitle("Foute email of paswoord");
        errorMessage.setMessage("Probeer opnieuw of neem contact op met je leraar.");

        toAddLoginForm.setI18n(i18n);
        toAddLoginForm.setAction("/login");

        add(
                logo,
               toAddLoginForm
        );
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        if (beforeEnterEvent.getLocation().getQueryParameters().getParameters().containsKey("error")){
            toAddLoginForm.setError(true);
        }
    }
}
