package com.example.Vaadin_REST_bib.views.lists.leerling;


import com.example.Vaadin_REST_bib.restClasses.Magazine;
import com.example.Vaadin_REST_bib.services.MagazineService;
import com.example.Vaadin_REST_bib.views.LLN_MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.function.SerializableBiConsumer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;

@PageTitle("Magazines | lijst")
@Route(value = "ll/magazines", layout = LLN_MainLayout.class) //wordt geladen binnen mainlayout
@RolesAllowed("USER")
public class LLMagazineList extends VerticalLayout {
    private final MagazineService magazineService;
    Grid<Magazine> grid = new Grid<Magazine>(Magazine.class);
    TextField filterText = new TextField();


    public LLMagazineList(MagazineService magazineService) {
        this.magazineService = magazineService;
        addClassName("magazine-list");
        setSizeFull();  //tot beneden
        configureGrid();
        add(
                getToolbar(),
                getInhoud()
        );
        updateGrid();
    }


    private void updateGrid() {
        grid.setItems(magazineService.getMagazines(filterText.getValue()).getData().getMagazines());
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
        filterText.addValueChangeListener(e->updateGrid());
        HorizontalLayout toolbar = new HorizontalLayout(filterText);
        toolbar.addClassName("toolbar");
        return toolbar;
    }


    private void configureGrid() {
        grid.addClassName("magazine-grid");
        grid.setSizeFull();
        grid.setColumns("naamtijdschrift", "jaargang","nummertijdschrift");
        grid.addColumn(createMagazineComponentRenderer()).setHeader("Status");
        grid.getColumns().forEach(col->col.setAutoWidth(true));
    }

    private static final SerializableBiConsumer<Span, Magazine> magazineComponentUpdater = (
            span, magazine) -> {
        boolean isUitgeleend = magazine.getUitgeleend();
        boolean isReserved = magazine.getGereserveerdDoor()!=null;
        String theme = String.format("badge %s",
                isUitgeleend&&isReserved ? "error" : (isReserved?"default":(isUitgeleend?"contrast":"success")));
        span.getElement().setAttribute("theme", theme);
        span.setText(isUitgeleend&&isReserved?"Uitg en gereserv":(isReserved?"Gereserveerd":(isUitgeleend?"Uitgeleend":"Beschikbaar")));
    };
    private static ComponentRenderer<Span, Magazine> createMagazineComponentRenderer() {
        return new ComponentRenderer<>(Span::new, magazineComponentUpdater);
    }
}
