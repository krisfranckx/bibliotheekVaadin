package com.example.Vaadin_REST_bib.views.lists;


import com.example.Vaadin_REST_bib.services.UserService;
import com.example.Vaadin_REST_bib.views.forms.RegistrationForm;
import com.example.Vaadin_REST_bib.views.forms.UserForm_ChangePasword;
import com.example.Vaadin_REST_bib.views.login.LoginView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@PageTitle("Bibliotheek | Registratie")
//@Route(value = "registration", layout = ADMIN_LL_MainLayout.class) //wordt geladen binnen mainlayout
@Route(value = "registration")
@AnonymousAllowed
public class RegisterView extends VerticalLayout {

    private RegistrationForm registrationForm;
    UserService userService;


    public RegisterView(UserService userService) {
        this.userService = userService;
        setSizeFull();  //tot beneden
        this.setAlignItems(Alignment.CENTER);
        setWidthFull();
        this.setAlignItems(Alignment.CENTER);
        this.setWidth("50%");
        this.setJustifyContentMode(JustifyContentMode.CENTER);
        configureUserform();

        add(
                registrationForm
        );

    }

    private void configureUserform() {
        registrationForm = new RegistrationForm(userService);
        //registrationForm.setWidth("25em");
        //registrationForm.addListener(UserForm_ChangePasword.SaveEvent.class, this::saveUser);
        //registrationForm.addListener(UserForm_ChangePasword.CloseEvent.class, e -> closeEditor());
    }


    private void closeEditor() {
        UI.getCurrent().navigate(LoginView.class);
    }

}
