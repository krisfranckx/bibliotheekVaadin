package com.example.Vaadin_REST_bib.views.lists.leraar;


import com.example.Vaadin_REST_bib.restClasses.LKBook;
import com.example.Vaadin_REST_bib.services.LKBookService;
import com.example.Vaadin_REST_bib.views.LKN_MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
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
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;

import static com.vaadin.flow.spring.data.VaadinSpringDataHelpers.toSpringDataSort;

@PageTitle("Boeken | lijst")
@Route(value = "lk/books", layout = LKN_MainLayout.class) //wordt geladen binnen mainlayout
@RolesAllowed("LK")
public class LKBookList extends VerticalLayout {
    private final LKBookService lkBookService;
    Grid<LKBook> grid = new Grid<>(LKBook.class);
    TextField filterText = new TextField();

    public LKBookList(LKBookService bookService) {
        this.lkBookService = bookService;
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
        grid.setItems(lkBookService.getBooks(filterText.getValue()).getData().getLKBooks());
    }

    private void updateGridDataProvider() {
        grid.setItems(query -> {
                    String sort = query.getSortOrders().size()>0?query.getSortOrders().stream().map(SortOrder::getSorted).findFirst().get():"auteur";
                    SortDirection direction = query.getSortOrders().size()>0?query.getSortOrders().stream().map(SortOrder::getDirection).findFirst().get():SortDirection.ASCENDING;
                    return lkBookService.findAllDataprovider( query.getPageSize(), query.getPage(),filterText.getValue(), sort, direction).toStream();
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
        //filterText.addValueChangeListener(e->updateGrid());
        filterText.addValueChangeListener(e->updateGridDataProvider());

        HorizontalLayout toolbar = new HorizontalLayout(filterText);
        toolbar.addClassName("toolbar");
        return toolbar;
    }


    private void configureGrid() {
        grid.addClassName("book-grid");
        grid.setSizeFull();
        grid.setColumns("titel", "auteur", "taal");
        grid.getColumnByKey("taal").setHeader("Categorie");
        grid.addColumn(createBookComponentRenderer()).setHeader("status");
        grid.getColumns().forEach(col->col.setAutoWidth(true));
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
