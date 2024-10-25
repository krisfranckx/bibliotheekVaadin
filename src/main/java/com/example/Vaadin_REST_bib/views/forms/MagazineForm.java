package com.example.Vaadin_REST_bib.views.forms;

import com.example.Vaadin_REST_bib.restClasses.Book;
import com.example.Vaadin_REST_bib.restClasses.Magazine;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;
import jakarta.annotation.security.RolesAllowed;

@RolesAllowed({"ADMIN", "USER"})
public class MagazineForm extends FormLayout {
    //TODO: bean validations bij Class Book nog toevoegen bv. @NotNull @Email...
    Binder<Magazine> binder = new BeanValidationBinder<>(Magazine.class);
    TextField naamtijdschrift = new TextField("Titel");
    TextField nummertijdschrift = new TextField("Nummer");
    TextField jaargang = new TextField("Jaargang");
    public IntegerField numberOfMagazines;
    Button btnSave = new Button("Save");
    Button btnDelete = new Button("Delete");
    Button btnCancel = new Button("Cancel");
    private Magazine magazine;


    public MagazineForm() {
        addClassName("magazine-form");
        binder.bindInstanceFields(this); //lukt omdat attributen hier dezelfde naam hebben als in de klasse Book

        numberOfMagazines = new IntegerField();
        numberOfMagazines.setMin(1);
        numberOfMagazines.setMax(20);
        numberOfMagazines.setValue(1);
        numberOfMagazines.setStepButtonsVisible(true);
        numberOfMagazines.setLabel("Aantal magazines");
        numberOfMagazines.setHelperText("Max 20");
        numberOfMagazines.setVisible(false);
        add(naamtijdschrift, nummertijdschrift, jaargang,numberOfMagazines, creatBtnLayout());
    }

    public void setMagazine(Magazine magazine){
        this.magazine = magazine;
        binder.readBean(magazine);
    }

    private Component creatBtnLayout() {
        btnSave.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        btnDelete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        btnCancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        btnSave.addClickListener(event->validateAndSave());
        //btnDelete.addClickListener(event -> fireEvent(new DeleteEvent(this, magazine)));
        btnDelete.addClickListener(event -> showConfirmationMessageDelete());
        btnCancel.addClickListener(event -> fireEvent(new CloseEvent(this)));

        btnSave.addClickShortcut(Key.ENTER);
        btnCancel.addClickShortcut(Key.ESCAPE);

        HorizontalLayout btn_row1 = new HorizontalLayout(btnSave, btnDelete, btnCancel);
        return new VerticalLayout(btn_row1);
    }

    private void validateAndSave() {
        try{
            binder.writeBean(magazine);
            fireEvent(new SaveEvent(this, magazine));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    //events
    public static abstract class MagazineFormEvent extends ComponentEvent<MagazineForm>{
        private Magazine magazine1;

        protected MagazineFormEvent(MagazineForm source, Magazine magazine1){
            super(source,false);
            this.magazine1 = magazine1;
        }
        public Magazine getMagazine1(){
            return magazine1;
        }
    }
    public static class SaveEvent extends MagazineFormEvent {
        SaveEvent(MagazineForm source, Magazine magazine){
            super(source, magazine);
        }
    }
    public static class DeleteEvent extends MagazineFormEvent {
        DeleteEvent(MagazineForm source, Magazine magazine){
            super(source, magazine);
        }
    }
    public static class CloseEvent extends MagazineFormEvent {
        CloseEvent(MagazineForm source){
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>>Registration addListener(Class<T> eventType, ComponentEventListener<T> listener){
        return getEventBus().addListener(eventType,listener);
    }
    private void showConfirmationMessageDelete(){
        ConfirmDialog dialog = new ConfirmDialog();
        dialog.setHeader("Magazine verwijderen");
        dialog.setText("Ben je zeker dat je dit magazine wil verwijderen?");
        dialog.setRejectable(true);
        dialog.setRejectText("Cancel");
        dialog.setConfirmText("Verwijderen");
        dialog.addConfirmListener(event -> fireEvent(new DeleteEvent(this,magazine)));
        dialog.open();
    }
}
