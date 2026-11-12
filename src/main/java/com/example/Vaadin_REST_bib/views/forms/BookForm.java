package com.example.Vaadin_REST_bib.views.forms;

import com.example.Vaadin_REST_bib.restClasses.Book;
import com.example.Vaadin_REST_bib.restClasses.Response;
import com.example.Vaadin_REST_bib.services.BookService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;
import jakarta.annotation.security.RolesAllowed;

public class BookForm extends FormLayout {
    //TODO: bean validations bij Class Book nog toevoegen bv. @NotNull @Email...
    Binder<Book> binder = new BeanValidationBinder<>(Book.class);
    TextField titel = new TextField("Titel");
    TextField auteur = new TextField("Auteur");
    TextField barcode = new TextField("Barcode / ISBN");
    ComboBox<String> taal = new ComboBox<>("Taal");
    ComboBox<String> graad = new ComboBox<>("Graad");
    ComboBox<String> fictieNonFictie = new ComboBox<>("Fictie / non-Fictie");
    public IntegerField numberOfBooks;
    Button btnSave = new Button("Save");
    Button btnDelete = new Button("Delete");
    Button btnCancel = new Button("Cancel");
    Button btnScan = new Button("Scan met camera");
    private Book book;
    private final BookService bookService;


    public BookForm(BookService bookService) {
        this.bookService = bookService;
        addClassName("book-form");
        binder.bindInstanceFields(this); //lukt omdat attributen hier dezelfde naam hebben als in de klasse Book
        taal.setItems("Frans","Engels", "algemeen");
        taal.setAllowCustomValue(false);
        taal.setPlaceholder("Selecteer de taal");
        graad.setItems("2de graad", "3de graad", "OKAN");
        graad.setAllowCustomValue(false);
        graad.setPlaceholder("Selecteer de graad");
        fictieNonFictie.setItems("Fictie", "non-Fictie");
        numberOfBooks = new IntegerField();
        numberOfBooks.setMin(1);
        numberOfBooks.setMax(20);
        numberOfBooks.setValue(1);
        numberOfBooks.setStepButtonsVisible(true);
        numberOfBooks.setLabel("Aantal boeken");
        numberOfBooks.setHelperText("Max 20");
        numberOfBooks.setVisible(false);

        btnScan.addClickListener(event -> scanBarcode());
        add(barcode, btnScan, titel, auteur, taal, graad, fictieNonFictie, numberOfBooks,creatBtnLayout());
    }

    public void setBook(Book book){
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

    private void scanBarcode() {
        UI.getCurrent().getPage().executeJs("""
                return (async () => {
                    if (typeof BarcodeDetector === 'undefined' || !navigator.mediaDevices?.getUserMedia) {
                        return 'UNSUPPORTED';
                    }
                    const video = document.createElement('video');
                    video.setAttribute('playsinline', '');
                    video.style.position = 'fixed';
                    video.style.inset = '10% 10% auto 10%';
                    video.style.width = '80%';
                    video.style.zIndex = '9999';
                    document.body.appendChild(video);
                    let stream;
                    try {
                        const detector = new BarcodeDetector({
                            formats: ['ean_13', 'ean_8', 'upc_a', 'upc_e', 'code_128']
                        });
                        stream = await navigator.mediaDevices.getUserMedia({
                            video: { facingMode: { ideal: 'environment' } }
                        });
                        video.srcObject = stream;
                        await video.play();
                        return await new Promise(resolve => {
                            const detect = async () => {
                                try {
                                    const codes = await detector.detect(video);
                                    if (codes.length > 0) {
                                        resolve(codes[0].rawValue);
                                        return;
                                    }
                                    requestAnimationFrame(detect);
                                } catch (error) {
                                    resolve('ERROR');
                                }
                            };
                            detect();
                        });
                    } catch (error) {
                        return 'ERROR';
                    } finally {
                        stream?.getTracks().forEach(track => track.stop());
                        video.remove();
                    }
                })();
                """).then(String.class, this::handleScannedBarcode);
    }

    private void handleScannedBarcode(String scannedBarcode) {
        if (scannedBarcode == null || scannedBarcode.equals("ERROR")) {
            Notification.show("De barcode kon niet worden gelezen.");
            return;
        }
        if (scannedBarcode.equals("UNSUPPORTED")) {
            Notification.show("Deze browser ondersteunt geen camerascan. Typ de barcode handmatig.");
            return;
        }

        barcode.setValue(scannedBarcode);
        Response response = bookService.getBookMetadataByBarcode(scannedBarcode);
        if (response == null || response.getData() == null || response.getData().getBook() == null) {
            Notification.show("Geen boekmetadata gevonden. Vul de gegevens handmatig aan.");
            return;
        }

        Book metadata = response.getData().getBook();
        if (metadata.getTitel() != null) {
            titel.setValue(metadata.getTitel());
        }
        if (metadata.getAuteur() != null) {
            auteur.setValue(metadata.getAuteur());
        }
        Notification.show("Boekgegevens opgehaald.");
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
    public static abstract class BookFormEvent extends ComponentEvent<BookForm>{
        private Book book;

        protected BookFormEvent(BookForm source, Book book){
            super(source,false);
            this.book = book;
        }
        public Book getBook(){
            return book;
        }
    }
    public static class SaveEvent extends BookFormEvent{
        SaveEvent(BookForm source, Book book){
            super(source, book);
        }
    }
    public static class DeleteEvent extends BookFormEvent{
        DeleteEvent(BookForm source, Book book){
            super(source, book);
        }
    }
    public static class CloseEvent extends BookFormEvent{
        CloseEvent(BookForm source){
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
