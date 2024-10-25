package com.example.Vaadin_REST_bib.views.forms;

import com.example.Vaadin_REST_bib.restClasses.User;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;
import jakarta.annotation.security.RolesAllowed;

@RolesAllowed({"ADMIN", "USER"})
public class UserForm extends FormLayout {
    //TODO: bean validations bij Class User nog toevoegen bv. @NotNull @Email...
    Binder<User> binder = new BeanValidationBinder<>(User.class);


    TextField voornaam = new TextField("Voornaam");
    TextField familienaam = new TextField("Familienaam");
    EmailField email = new EmailField("Email");
    Button btnSave = new Button("Save");
    Button btnDelete = new Button("Delete");
    Button btnCancel = new Button("Cancel");
    private User user;


    public UserForm() {
        addClassName("user-form");
        binder.bindInstanceFields(this); //lukt omdat attributen hier dezelfde naam hebben als in de klasse User
        add(voornaam, familienaam, email, creatBtnLayout());
    }

    public void setUser(User user){
        this.user = user;
        binder.readBean(user);
    }

    private Component creatBtnLayout() {
        btnSave.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        btnDelete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        btnCancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        btnSave.addClickListener(event->validateAndSave());
        btnDelete.addClickListener(event -> showConfirmationMessageDelete());
        btnCancel.addClickListener(event -> fireEvent(new CloseEvent(this)));

        btnSave.addClickShortcut(Key.ENTER);
        btnCancel.addClickShortcut(Key.ESCAPE);

        return new HorizontalLayout(btnSave, btnDelete, btnCancel);
    }

    private void validateAndSave() {
        try{
            binder.writeBean(user);
               fireEvent(new SaveEvent(this, user));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    //events
    public static abstract class UserFormEvent extends ComponentEvent<UserForm>{
        private User user;
        protected UserFormEvent(UserForm source, User user){
            super(source,false);
            this.user = user;
        }
        public User getUser(){
            return user;
        }
    }
    public static class SaveEvent extends UserFormEvent{
        SaveEvent(UserForm source, User user){
            super(source, user);
        }
    }
    public static class DeleteEvent extends UserFormEvent{
        DeleteEvent(UserForm source, User user){
            super(source, user);
        }
    }
    public static class CloseEvent extends UserFormEvent{
        CloseEvent(UserForm source){
            super(source, null);
        }
    }
    public <T extends ComponentEvent<?>>Registration addListener(Class<T> eventType, ComponentEventListener<T> listener){
        return getEventBus().addListener(eventType,listener);
    }

    private void showConfirmationMessageDelete(){
        ConfirmDialog dialog = new ConfirmDialog();
        dialog.setHeader("Gebruiker verwijderen");
        dialog.setText("Ben je zeker dat je deze gebruiker wil verwijderen?");
        dialog.setRejectable(true);
        dialog.setRejectText("Cancel");
        dialog.setConfirmText("Verwijderen");
        dialog.addConfirmListener(event -> fireEvent(new DeleteEvent(this,user)));
        dialog.open();
    }
}
