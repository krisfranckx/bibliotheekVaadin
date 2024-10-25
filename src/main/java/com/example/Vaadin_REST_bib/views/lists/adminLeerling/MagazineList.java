package com.example.Vaadin_REST_bib.views.lists.adminLeerling;


import com.example.Vaadin_REST_bib.restClasses.Magazine;
import com.example.Vaadin_REST_bib.restClasses.Response;
import com.example.Vaadin_REST_bib.services.MagazineService;
import com.example.Vaadin_REST_bib.views.ADMIN_LL_MainLayout;
import com.example.Vaadin_REST_bib.views.forms.MagazineForm;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
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
@Route(value = "ll/admin/magazines", layout = ADMIN_LL_MainLayout.class) //wordt geladen binnen mainlayout
@RolesAllowed("ADMIN")
public class MagazineList extends VerticalLayout {
    private final MagazineService magazineService;
    Grid<Magazine> grid = new Grid<Magazine>(Magazine.class);
    TextField filterText = new TextField();
    MagazineForm magazineForm;


    public MagazineList(MagazineService magazineService) {
        this.magazineService = magazineService;
        addClassName("magazine-list");
        setSizeFull();  //tot beneden
        configureGrid();
        configureMagazineform();
        add(
                getToolbar(),
                getInhoud()
        );
        updateGrid();
        closeEditor();
    }

    private void closeEditor() {
        this.getMagazineForm().numberOfMagazines.setValue(1);
        magazineForm.setMagazine(null);
        magazineForm.setVisible(false);
    }

    private void updateGrid() {
        grid.setItems(magazineService.getMagazines(filterText.getValue()).getData().getMagazines());
    }

    private Component getInhoud() {
        HorizontalLayout inhoud = new HorizontalLayout(grid, magazineForm);
        inhoud.setFlexGrow(2,grid);
        inhoud.setFlexGrow(1, magazineForm);
        inhoud.addClassName("inhoud");
        inhoud.setSizeFull();
        return inhoud;
    }

    private void configureMagazineform() {
        magazineForm = new MagazineForm();
        magazineForm.setWidth("25em");
        magazineForm.addListener(MagazineForm.SaveEvent.class,this::saveMagazine);
        magazineForm.addListener(MagazineForm.DeleteEvent.class,this::deleteMagazine);
        magazineForm.addListener(MagazineForm.CloseEvent.class, e->closeEditor());
    }

    private void saveMagazine(MagazineForm.SaveEvent event) {
        for (int i = 0; i < this.getMagazineForm().numberOfMagazines.getValue(); i++) {
            boolean newMagazine = event.getMagazine1().getMagazineId() == null;
            Response response;
            if (newMagazine){
                response= magazineService.saveMagazine(event.getMagazine1());
            } else {
                response= magazineService.updateMagazine(event.getMagazine1());

            }
            String message = response.getMessage();
            Notification.show(message, 5000, Notification.Position.TOP_CENTER);
        }

        updateGrid();
        this.getMagazineForm().numberOfMagazines.setValue(1);
        closeEditor();
    }
    private void deleteMagazine(MagazineForm.DeleteEvent event) {

        Response response = magazineService.deleteMagazine(event.getMagazine1());
        String message = response.getMessage();
        Notification.show(message, 5000, Notification.Position.TOP_CENTER);
        updateGrid();
        this.getMagazineForm().numberOfMagazines.setValue(1);
        closeEditor();
    }

    private Component getToolbar() {
        filterText.setPlaceholder("filter");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e->updateGrid());
        Button btn_magazineToevoegen = new Button("Magazine toevoegen");
        btn_magazineToevoegen.addClickListener(e-> addMagazine());
        HorizontalLayout toolbar = new HorizontalLayout(filterText, btn_magazineToevoegen);
        toolbar.addClassName("toolbar");
        return toolbar;
    }
    private void addMagazine() {
        grid.asSingleSelect().clear();
        editContact(new Magazine(), "newMagazine");
    }

    private void configureGrid() {
        grid.addClassName("magazine-grid");
        grid.setSizeFull();
        grid.setColumns("naamtijdschrift", "jaargang","code_tijdschrift");
        grid.addColumn(createMagazineComponentRenderer()).setHeader("Status");
        grid.getColumns().forEach(col->col.setAutoWidth(true));
        grid.asSingleSelect().addValueChangeListener(e->editContact(e.getValue(),""));
    }
    private void editContact(Magazine magazine, String newMagazine) {
        if (magazine == null){
            closeEditor();
        }else{
            if (newMagazine.equalsIgnoreCase("newMagazine")){ //new magazine(s)
                magazineForm.numberOfMagazines.setVisible(true);
            } else {
                magazineForm.numberOfMagazines.setVisible(false);
            }
            magazineForm.setMagazine(magazine);
            magazineForm.setVisible(true);
        }
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

    public MagazineForm getMagazineForm() {
        return magazineForm;
    }
}
