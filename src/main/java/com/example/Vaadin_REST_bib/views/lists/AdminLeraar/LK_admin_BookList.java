package com.example.Vaadin_REST_bib.views.lists.AdminLeraar;


import com.example.Vaadin_REST_bib.restClasses.Book;
import com.example.Vaadin_REST_bib.restClasses.LKBook;
import com.example.Vaadin_REST_bib.restClasses.Response;
import com.example.Vaadin_REST_bib.services.BookService;
import com.example.Vaadin_REST_bib.services.LKBookService;
import com.example.Vaadin_REST_bib.views.ADMIN_LK_MainLayout;
import com.example.Vaadin_REST_bib.views.ADMIN_LL_MainLayout;
import com.example.Vaadin_REST_bib.views.forms.BookForm;
import com.example.Vaadin_REST_bib.views.forms.LKBookForm;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.data.provider.SortOrder;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.function.SerializableBiConsumer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;

import java.util.Comparator;

import static com.vaadin.flow.spring.data.VaadinSpringDataHelpers.toSpringDataSort;

@PageTitle("Boeken | lijst")
@Route(value = "lk/admin/books", layout = ADMIN_LK_MainLayout.class) //wordt geladen binnen mainlayout
@RolesAllowed( "LKADMIN")
public class LK_admin_BookList extends VerticalLayout {
    private final LKBookService bookService;
    Grid<LKBook> grid = new Grid<>(LKBook.class);
    TextField filterText = new TextField();
    LKBookForm bookForm;

    public LKBookForm getBookForm() {
        return bookForm;
    }

    public LK_admin_BookList(LKBookService bookService) {
        this.bookService = bookService;
        addClassName("book-list");
        setSizeFull();
        configureGrid();
        configureBookform();

        add(
                getToolbar(),
                getInhoud()
        );

        //updateGrid();
        updateGridDataProvider();
        closeEditor();
    }

    private void closeEditor() {
        this.getBookForm().numberOfBooks.setValue(1);
        bookForm.setBook(null);
        bookForm.setVisible(false);
    }

    private void updateGrid() {
        grid.setItems(bookService.getBooks(filterText.getValue()).getData().getLKBooks());
    }

    private void updateGridDataProvider() {
        grid.setItems(query -> {
            String sort = query.getSortOrders().size()>0?query.getSortOrders().stream().map(SortOrder::getSorted).findFirst().get():"auteur";
            SortDirection direction = query.getSortOrders().size()>0?query.getSortOrders().stream().map(SortOrder::getDirection).findFirst().get():SortDirection.ASCENDING;
            return bookService.findAllDataprovider( query.getPageSize(), query.getPage(), filterText.getValue(), sort, direction).toStream();
                }
        );
    }

    private Component getInhoud() {
        HorizontalLayout inhoud = new HorizontalLayout(grid, bookForm);
        inhoud.setFlexGrow(2,grid);
        inhoud.setFlexGrow(1,bookForm);
        inhoud.addClassName("inhoud");
        inhoud.setSizeFull();
        return inhoud;
    }

    private void configureBookform() {
        bookForm = new LKBookForm();
        bookForm.setWidth("25em");
        bookForm.addListener(LKBookForm.SaveEvent.class,this::saveBook);
        bookForm.addListener(LKBookForm.DeleteEvent.class,this::deleteBook);
        bookForm.addListener(BookForm.CloseEvent.class,e->closeEditor());
    }

    private void saveBook(LKBookForm.SaveEvent event) {
        for (int i = 0; i < this.getBookForm().numberOfBooks.getValue(); i++) {
            boolean newBook = event.getBook().getBoekId() == null;
            Response response;
            if(newBook){
                response = bookService.saveBook(event.getBook());
            } else {
                response = bookService.updateBook(event.getBook());
            }
            String message = response.getMessage();
            Notification.show(message, 5000, Notification.Position.TOP_CENTER);
        }

        //updateGrid();
        updateGridDataProvider();
        this.getBookForm().numberOfBooks.setValue(1);
        closeEditor();
    }
    private void deleteBook(LKBookForm.DeleteEvent event) {
        Response response = bookService.deleteBook(event.getBook());
        String message = response.getMessage();
        Notification.show(message, 5000, Notification.Position.TOP_CENTER);
        //updateGrid();
        updateGridDataProvider();
        closeEditor();
    }

    private Component getToolbar() {
        filterText.setPlaceholder("filter");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        //filterText.addValueChangeListener(e->updateGrid());
        filterText.addValueChangeListener(e->updateGridDataProvider());
        Button btn_boekToevoegen = new Button("Boek toevoegen");
        btn_boekToevoegen.addClickListener(e->addBook());
        HorizontalLayout toolbar = new HorizontalLayout(filterText, btn_boekToevoegen);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void addBook() {
        grid.asSingleSelect().clear();
        editContact(new LKBook(), "newBook");
    }

    private void configureGrid() {
        grid.addClassName("book-grid");
        grid.setSizeFull();
        grid.setColumns("titel", "auteur", "taal");
        grid.addColumn(LKBook::getCode).setHeader("Code").setSortable(false);
        grid.getColumnByKey("taal").setHeader("Categorie");
        grid.addColumn(createBookComponentRenderer()).setHeader("Status");
                //.setSortable(true).setComparator(Comparator.comparing(LKBook::getUitgeleend));
        grid.getColumns().forEach(col->col.setAutoWidth(true));
        grid.asSingleSelect().addValueChangeListener(e->editContact(e.getValue(),""));
    }

    private void editContact(LKBook book, String newbook) {
        if (book == null){
            closeEditor();
        }else{
            if (newbook.equalsIgnoreCase("newbook")){ //new book(s)
                bookForm.numberOfBooks.setVisible(true);
            } else {
                bookForm.numberOfBooks.setVisible(false);
            }
            bookForm.setBook(book);
            bookForm.setVisible(true);
        }
    }

    private static final SerializableBiConsumer<Span, LKBook> bookComponentUpdater = (
            span, book) -> {
        boolean isUitgeleend = book.getUitgeleend();
        boolean isReserved = book.getGereserveerdDoor()!=null;
        String theme = String.format("badge %s",
                isUitgeleend&&isReserved ? "error" : (isReserved?"default":(isUitgeleend?"contrast":"success")));
        span.getElement().setAttribute("theme", theme);
        span.setText(isUitgeleend&&isReserved?"Uitg en gereserv":(isReserved?"Gereserveerd":(isUitgeleend?"Uitgeleend":"Beschikbaar")));
    };

    private static ComponentRenderer<Span, LKBook> createBookComponentRenderer() {
        return new ComponentRenderer<>(Span::new, bookComponentUpdater);
    }
}
