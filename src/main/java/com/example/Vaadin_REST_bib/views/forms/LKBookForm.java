package com.example.Vaadin_REST_bib.views.forms;

import com.example.Vaadin_REST_bib.restClasses.Book;
import com.example.Vaadin_REST_bib.restClasses.Categorie_LK;
import com.example.Vaadin_REST_bib.restClasses.LKBook;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
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

import java.util.Arrays;
import java.util.List;

public class LKBookForm extends FormLayout {
    //TODO: bean validations bij Class Book nog toevoegen bv. @NotNull @Email...
    Binder<LKBook> binder = new BeanValidationBinder<>(LKBook.class);
    TextField titel = new TextField("Titel");
    TextField auteur = new TextField("Auteur");
    ComboBox<String> taal = new ComboBox<>("Categorie");
    //ComboBox<String> graad = new ComboBox<>("Graad");
    //ComboBox<String> fictieNonFictie = new ComboBox<>("Fictie / non-Fictie");
    public IntegerField numberOfBooks;
    Button btnSave = new Button("Save");
    Button btnDelete = new Button("Delete");
    Button btnCancel = new Button("Cancel");
    private LKBook book;


    public LKBookForm() {
        addClassName("book-form");
        binder.bindInstanceFields(this); //lukt omdat attributen hier dezelfde naam hebben als in de klasse Book
        taal.setItems(setItemsCombo());
        taal.setAllowCustomValue(false);
        taal.setPlaceholder("Selecteer de categorie");
/*        graad.setItems("2de graad", "3de graad", "OKAN");
        graad.setAllowCustomValue(false);
        graad.setPlaceholder("Selecteer de graad");*/
        //fictieNonFictie.setItems("Fictie", "non-Fictie");
        numberOfBooks = new IntegerField();
        numberOfBooks.setMin(1);
        //numberOfBooks.setMax(20);
        numberOfBooks.setValue(1);
        numberOfBooks.setStepButtonsVisible(true);
        numberOfBooks.setLabel("Aantal boeken");
        numberOfBooks.setHelperText("Max 20");
        numberOfBooks.setVisible(false);

        add(titel, auteur, taal, numberOfBooks,creatBtnLayout());
    }

    private List<String> setItemsCombo() {
        List<Categorie_LK> list = Arrays.stream(Categorie_LK.values()).toList();
        List<String> stringlist = list.stream().map(Categorie_LK::getCategorie).toList();
        return stringlist;
    }

    public void setBook(LKBook book){
        this.book = book;
        binder.readBean(book);
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

        HorizontalLayout btn_row1 = new HorizontalLayout(btnSave, btnDelete, btnCancel);
        return new VerticalLayout(btn_row1);
    }

    private void validateAndSave() {
        try{
            binder.writeBean(book);
            fireEvent(new SaveEvent(this,book));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    //events
    public static abstract class BookFormEvent extends ComponentEvent<LKBookForm>{
        private LKBook book;

        protected BookFormEvent(LKBookForm source, LKBook book){
            super(source,false);
            this.book = book;
        }
        public LKBook getBook(){
            return book;
        }
    }
    public static class SaveEvent extends BookFormEvent{
        SaveEvent(LKBookForm source, LKBook book){
            super(source, book);
        }
    }
    public static class DeleteEvent extends BookFormEvent{
        DeleteEvent(LKBookForm source, LKBook book){
            super(source, book);
        }
    }
    public static class CloseEvent extends BookFormEvent{
        CloseEvent(LKBookForm source){
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>>Registration addListener(Class<T> eventType, ComponentEventListener<T> listener){
        return getEventBus().addListener(eventType,listener);
    }

    private void showConfirmationMessageDelete(){
        ConfirmDialog dialog = new ConfirmDialog();
        dialog.setHeader("Boek verwijderen");
        dialog.setText("Ben je zeker dat je dit boek wil verwijderen?");
        dialog.setRejectable(true);
        dialog.setRejectText("Cancel");
        dialog.setConfirmText("Verwijderen");
        dialog.addConfirmListener(event -> fireEvent(new DeleteEvent(this,book)));
        dialog.open();
    }

}
