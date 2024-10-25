package com.example.Vaadin_REST_bib.views.forms;

import com.example.Vaadin_REST_bib.restClasses.User;
import com.example.Vaadin_REST_bib.services.UserService;
import com.example.Vaadin_REST_bib.views.login.LoginView;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.shared.Registration;

import java.util.List;

public class RegistrationForm extends FormLayout {
    //TODO: bean validations bij Class User nog toevoegen bv. @NotNull @Email...
    TextField voornaam = new TextField("Voornaam");
    TextField familienaam = new TextField("Familienaam");
    EmailField email = new EmailField("Email");
    PasswordField password = new PasswordField("Paswoord");
    PasswordField confirmPassword = new PasswordField("Bevestig paswoord");
    Button btnSave = new Button("Registreer");
    Button btnCancel = new Button("Cancel");
    UserService userService;
    Boolean emailOccupied = true;
    Boolean passwordsCorrect = false;
    RadioButtonGroup<String> radioGroup;





    public RegistrationForm(UserService userService) {
        this.setSizeFull();
        this.setHeightFull();
        this.userService = userService;
        password.setValueChangeMode(ValueChangeMode.EAGER);
        confirmPassword.setValueChangeMode(ValueChangeMode.EAGER);
        email.setValueChangeMode(ValueChangeMode.EAGER);
        voornaam.setValueChangeMode(ValueChangeMode.EAGER);
        familienaam.setValueChangeMode(ValueChangeMode.EAGER);

        password.addKeyUpListener(e->{passwordsCorrect();checkSaveButton();});
        confirmPassword.addKeyUpListener(e->{passwordsCorrect();checkSaveButton();});
        email.addKeyUpListener(e->{
            if(!email.isInvalid()){
                checkUsernameOccupied();
            }
            checkSaveButton();
        });
        email.addBlurListener(e->checkUsernameOccupied());
        voornaam.addKeyUpListener(e->checkSaveButton());
        familienaam.addKeyUpListener(e->checkSaveButton());

        email.setErrorMessage("Dit is geen geldig e-mailadres");

        radioGroup = new RadioButtonGroup<>();
        //radioGroup.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
        radioGroup.setLabel("Functie");
        radioGroup.setItems("Leerling", "Leraar");
        radioGroup.setValue("Leerling");

        add(voornaam, familienaam, email,radioGroup, password, confirmPassword, creatBtnLayout());
    }

    private Component creatBtnLayout() {
        btnSave.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        btnCancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        btnSave.addClickListener(event->register());
        btnCancel.addClickListener(event ->  UI.getCurrent().navigate(LoginView.class));

        btnSave.addClickShortcut(Key.ENTER);
        btnCancel.addClickShortcut(Key.ESCAPE);

        btnSave.setEnabled(false);

        return new HorizontalLayout(btnSave, btnCancel);
    }

    private void register() {
        User user = new User();
        user.setVoornaam(voornaam.getValue());
        user.setFamilienaam(familienaam.getValue());
        user.setEmail(email.getValue());
        user.setPaswoord(password.getValue());
        user.setFunction(radioGroup.getValue());
        userService.register(user);
        UI.getCurrent().navigate("/registered");
    }


    private void checkSaveButton() {
        if (!voornaam.getValue().isEmpty()
                &&!familienaam.getValue().isEmpty()
                &&!email.getValue().isEmpty()
                &&!email.isInvalid()
                &&!password.getValue().isEmpty()
                &&!confirmPassword.isEmpty()
                &&!emailOccupied
                &&passwordsCorrect
        ){
            btnSave.setEnabled(true);
        } else {
            btnSave.setEnabled(false);
        }
    }



    private Boolean checkUsernameOccupied() {
        emailOccupied = false;
        List<User> userlist = userService.getUsers("").getData().getUsers();
        userlist.forEach(user->{
            if (email.getValue().trim().equalsIgnoreCase(user.getEmail().trim())){
                ConfirmDialog dialog = new ConfirmDialog();
                dialog.setHeader("Email al in gebruik");
                dialog.setText("Dit emailadres is al in gebruik. Klik op Cancel en log in, of kies een ander emailadres.");
                dialog.setConfirmText("OK");
                dialog.addConfirmListener(event -> dialog.close());
                dialog.open();
                emailOccupied=true;
            }
        });
        checkSaveButton();
        return emailOccupied;

    }
    private Boolean passwordsCorrect() {
        if (password.getValue().equals(confirmPassword.getValue())) {
            passwordsCorrect=true;
            checkSaveButton();
        }else {
            passwordsCorrect=false;
            checkSaveButton();

        }
        return passwordsCorrect;
    }

    //events
    public static abstract class RegistrationFormEvent extends ComponentEvent<RegistrationForm>{

        protected RegistrationFormEvent(RegistrationForm source){
            super(source,false);
        }
    }
    public static class SaveEvent extends RegistrationFormEvent {
        SaveEvent(RegistrationForm source){
            super(source);
        }
    }

    public static class CloseEvent extends RegistrationFormEvent {
        CloseEvent(RegistrationForm source){
            super(source);
        }
    }
    public <T extends ComponentEvent<?>>Registration addListener(Class<T> eventType, ComponentEventListener<T> listener){
        return getEventBus().addListener(eventType,listener);
    }
}
