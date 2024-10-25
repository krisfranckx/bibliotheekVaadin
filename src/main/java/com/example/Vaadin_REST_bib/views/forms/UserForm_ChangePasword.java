package com.example.Vaadin_REST_bib.views.forms;

import com.example.Vaadin_REST_bib.restClasses.User;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.shared.Registration;
import jakarta.annotation.security.RolesAllowed;

import java.util.Objects;

@RolesAllowed({"ADMIN", "USER"})
public class UserForm_ChangePasword extends FormLayout {
    //TODO: bean validations bij Class User nog toevoegen bv. @NotNull @Email...
    Binder<User> binder = new BeanValidationBinder<>(User.class);
    TextField voornaam = new TextField("Voornaam");
    TextField familienaam = new TextField("Familienaam");
    TextField email = new TextField("Email");
    PasswordField password = new PasswordField("Paswoord");
    PasswordField confirmPassword = new PasswordField("Bevestig paswoord");
    Button btnSave = new Button("Save");
    Button btnCancel = new Button("Cancel");
    private User user;


    public UserForm_ChangePasword() {
        addClassName("user-form");
        //binder.bindInstanceFields(this); //lukt omdat attributen hier dezelfde naam hebben als in de klasse User
        binder.forField(voornaam).bind(User::getVoornaam, User::setVoornaam);
        binder.forField(familienaam).bind(User::getFamilienaam, User::setFamilienaam);
        binder.forField(email).bind(User::getEmail, User::setEmail);
        binder.forField(password).bind(User::getPaswoord, User::setPaswoord);
        email.setReadOnly(true);
        voornaam.setReadOnly(true);
        familienaam.setReadOnly(true);
        password.setValueChangeMode(ValueChangeMode.EAGER);
        confirmPassword.setValueChangeMode(ValueChangeMode.EAGER);
        password.addKeyUpListener(e->checkSaveButton());
        confirmPassword.addKeyUpListener(e->checkSaveButton());
        add(voornaam, familienaam, email, password, confirmPassword, creatBtnLayout());
    }

    public void setUser(User user){
        this.user = user;
        binder.readBean(user);
        password.setValue("");
        confirmPassword.setValue("");
    }

    private Component creatBtnLayout() {
        btnSave.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        btnCancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        btnSave.addClickListener(event->validateAndSave());
        btnCancel.addClickListener(event -> fireEvent(new CloseEvent(this)));
        btnSave.addClickShortcut(Key.ENTER);
        btnCancel.addClickShortcut(Key.ESCAPE);
        btnSave.setEnabled(false);
        return new HorizontalLayout(btnSave, btnCancel);
    }

    private void validateAndSave() {
        try{
            binder.writeBean(user);
               fireEvent(new SaveEvent(this, user));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }
    public User getUser(){
        return user;
    }

    private void checkSaveButton() {
        if (!password.getValue().isEmpty() && !confirmPassword.isEmpty()) {
            if (password.getValue().equals(confirmPassword.getValue())) {
                btnSave.setEnabled(true);
            }else {
                btnSave.setEnabled(false);
            }
        } else {
            btnSave.setEnabled(false);
        }
    }
    //events
    public static abstract class UserFormEvent extends ComponentEvent<UserForm_ChangePasword>{
        private User user;
        protected UserFormEvent(UserForm_ChangePasword source, User user){
            super(source,false);
            this.user = user;
        }
        public User getUser(){
            return user;
        }
    }
    public static class SaveEvent extends UserFormEvent{
        SaveEvent(UserForm_ChangePasword source, User user){
            super(source, user);
        }
    }

    public static class CloseEvent extends UserFormEvent{
        CloseEvent(UserForm_ChangePasword source){
            super(source, null);
        }
    }
    public <T extends ComponentEvent<?>>Registration addListener(Class<T> eventType, ComponentEventListener<T> listener){
        return getEventBus().addListener(eventType,listener);
    }
}
