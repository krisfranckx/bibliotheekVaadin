package com.example.Vaadin_REST_bib.views.lists.leerling;


import com.example.Vaadin_REST_bib.restClasses.Book;
import com.example.Vaadin_REST_bib.restClasses.Response;
import com.example.Vaadin_REST_bib.services.BookService;
import com.example.Vaadin_REST_bib.views.LKN_MainLayout;
import com.example.Vaadin_REST_bib.views.LLN_MainLayout;
import com.example.Vaadin_REST_bib.views.forms.BookForm;
import com.vaadin.flow.component.Component;
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
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;

@PageTitle("Boeken | lijst")
@Route(value = "ll/books", layout = LLN_MainLayout.class) //wordt geladen binnen mainlayout
@RolesAllowed("USER")
public class LLBookList extends VerticalLayout {
    private final BookService bookService;
    Grid<Book> grid = new Grid<Book>(Book.class);
    TextField filterText = new TextField();

    public LLBookList(BookService bookService) {
        this.bookService = bookService;
        addClassName("book-list");
        setSizeFull();  //tot beneden
        configureGrid();

        add(
                getToolbar(),
                getInhoud()
        );
        //updateGrid();
        updateGridDataProvider();
    }


    private void updateGrid() {
        grid.setItems(bookService.getBooks(filterText.getValue()).getData().getBooks());
    }


    private void updateGridDataProvider() {
        grid.setItems(query -> {
                    String sort = query.getSortOrders().size()>0?query.getSortOrders().stream().map(SortOrder::getSorted).findFirst().get():"auteur";
                    SortDirection direction = query.getSortOrders().size()>0?query.getSortOrders().stream().map(SortOrder::getDirection).findFirst().get():SortDirection.ASCENDING;
                    return bookService.findAllDataprovider( query.getPageSize(), query.getPage(), filterText.getValue(),sort, direction).toStream();

                }
        );
    }

    private Component getInhoud() {
        HorizontalLayout inhoud = new HorizontalLayout(grid);
        inhoud.addClassName("inhoud");
        inhoud.setSizeFull();
        return inhoud;
    }

    private Component getToolbar() {
        filterText.setPlaceholder("filter");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e->
                //updateGrid()
                updateGridDataProvider()
        );

        HorizontalLayout toolbar = new HorizontalLayout(filterText);
        toolbar.addClassName("toolbar");
        return toolbar;
    }


    private void configureGrid() {
        grid.addClassName("book-grid");
        grid.setSizeFull();
        grid.setColumns("titel", "auteur", "taal", "graad");
        grid.addColumn(createBookComponentRenderer()).setHeader("status");
        grid.getColumns().forEach(col->col.setAutoWidth(true));
    }


    private static final SerializableBiConsumer<Span, Book> bookComponentUpdater = (
            span, book) -> {
        boolean isUitgeleend = book.getUitgeleend();
        boolean isReserved = book.getGereserveerdDoor()!=null;
        String theme = String.format("badge %s",
                isUitgeleend&&isReserved ? "error" : (isReserved?"default":(isUitgeleend?"contrast":"success")));
        span.getElement().setAttribute("theme", theme);
        span.setText(isUitgeleend&&isReserved?"Uitg en gereserv":(isReserved?"Gereserveerd":(isUitgeleend?"Uitgeleend":"Beschikbaar")));
    };

    private static ComponentRenderer<Span, Book> createBookComponentRenderer() {
        return new ComponentRenderer<>(Span::new, bookComponentUpdater);
    }
}
