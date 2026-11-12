package com.example.Vaadin_REST_bib.views.lists;

import com.example.Vaadin_REST_bib.restClasses.User;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.details.DetailsVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.H6;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

public class BorrowListUserDetails extends Details {

    public record BorrowedItem(String type, String titel, String extra, String code, LocalDate uitgeleendTot,
                               Runnable bringBack, Runnable extend) {
        boolean isOverdue() {
            return uitgeleendTot != null && uitgeleendTot.isBefore(LocalDate.now());
        }
    }

    public BorrowListUserDetails(User user, List<BorrowedItem> items) {
        long overdue = items.stream().filter(BorrowedItem::isOverdue).count();
        String text = String.format("%s %s geleend", items.size(), items.size() > 1 ? "items" : "item");
        if (overdue > 0) {
            text += String.format(" | %s te laat", overdue);
        }
        setSummary(new VerticalLayout(new H5(user.getVoornaam() + " " + user.getFamilienaam()), new H6(text)));
        addThemeVariants(DetailsVariant.FILLED);
        if (overdue > 0) {
            getStyle().set("background-color", "rgba(217, 104, 85, 0.18)");
        }

        Grid<BorrowedItem> grid = new Grid<>();
        grid.addThemeVariants(GridVariant.LUMO_COMPACT, GridVariant.LUMO_NO_BORDER);
        grid.addColumn(BorrowedItem::type).setHeader("Type").setAutoWidth(true);
        grid.addColumn(BorrowedItem::titel).setHeader("Titel").setAutoWidth(true).setFlexGrow(1);
        grid.addColumn(BorrowedItem::extra).setHeader("Auteur / nummer").setAutoWidth(true);
        grid.addColumn(BorrowedItem::code).setHeader("Code").setAutoWidth(true);
        grid.addColumn(new ComponentRenderer<>(item -> {
            Span span = new Span(item.uitgeleendTot() == null ? "-" : item.uitgeleendTot().toString());
            if (item.isOverdue()) {
                span.getStyle().set("color", "var(--lumo-error-color)").set("font-weight", "700");
            }
            return span;
        })).setHeader("Terug in te leveren op").setAutoWidth(true)
                .setComparator(Comparator.comparing(BorrowedItem::uitgeleendTot, Comparator.nullsLast(Comparator.naturalOrder())));
        grid.addColumn(new ComponentRenderer<>(item -> {
            Button back = new Button(VaadinIcon.DOWNLOAD_ALT.create(), e -> item.bringBack().run());
            back.setTooltipText("Inleveren");
            Button extend = new Button(VaadinIcon.FAST_FORWARD.create(), e -> item.extend().run());
            extend.setTooltipText("Verlengen");
            back.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
            extend.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
            return new HorizontalLayout(back, extend);
        })).setHeader("Acties").setAutoWidth(true);
        grid.setAllRowsVisible(true);
        grid.setItems(items.stream()
                .sorted(Comparator.comparing(BorrowedItem::uitgeleendTot, Comparator.nullsLast(Comparator.naturalOrder())))
                .toList());

        VerticalLayout content = new VerticalLayout(grid);
        content.setPadding(false);
        add(content);
    }
}
