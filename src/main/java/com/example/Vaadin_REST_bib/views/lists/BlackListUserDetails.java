package com.example.Vaadin_REST_bib.views.lists;

import com.example.Vaadin_REST_bib.restClasses.Book;
import com.example.Vaadin_REST_bib.restClasses.LKBook;
import com.example.Vaadin_REST_bib.restClasses.Magazine;
import com.example.Vaadin_REST_bib.restClasses.User;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.details.DetailsVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.H6;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class BlackListUserDetails extends Details {

    private static final int MAX_ITEMS = 3;

    public record LoanedItem(String type, String titel, String code, LocalDate uitgeleendTot) {
        boolean isOverdue() {
            return uitgeleendTot != null && uitgeleendTot.isBefore(LocalDate.now());
        }

        String teLaat() {
            if (!isOverdue()) {
                return "-";
            }
            long days = ChronoUnit.DAYS.between(uitgeleendTot, LocalDate.now());
            return days + (days == 1 ? " dag" : " dagen");
        }
    }

    public BlackListUserDetails(User user, List<LoanedItem> items) {
        long overdue = items.stream().filter(LoanedItem::isOverdue).count();
        boolean disabled = Boolean.FALSE.equals(user.getEnabledUI());

        List<String> reasons = new ArrayList<>();
        if (overdue > 0) {
            reasons.add(String.format("%s %s te laat teruggebracht", overdue, overdue > 1 ? "items" : "item"));
        }
        if (disabled) {
            reasons.add(items.size() >= MAX_ITEMS
                    ? String.format("Maximum van %s geleende items bereikt (%s geleend)", MAX_ITEMS, items.size())
                    : "Uitlenen is uitgeschakeld");
        }

        H6 summary = new H6(String.join(" | ", reasons));
        setSummary(new VerticalLayout(new H5(user.getVoornaam() + " " + user.getFamilienaam()), summary));
        addThemeVariants(DetailsVariant.FILLED);

        Grid<LoanedItem> grid = new Grid<>();
        grid.addThemeVariants(GridVariant.LUMO_COMPACT, GridVariant.LUMO_NO_BORDER);
        grid.addColumn(LoanedItem::type).setHeader("Type").setAutoWidth(true);
        grid.addColumn(LoanedItem::titel).setHeader("Titel").setAutoWidth(true).setFlexGrow(1);
        grid.addColumn(LoanedItem::code).setHeader("Code").setAutoWidth(true);
        grid.addColumn(LoanedItem::uitgeleendTot).setHeader("Terug op").setAutoWidth(true);
        grid.addColumn(LoanedItem::teLaat).setHeader("Te laat").setAutoWidth(true);
        grid.setAllRowsVisible(true);
        grid.setItems(items);

        VerticalLayout content = new VerticalLayout(grid);
        if (user.getEmail() != null) {
            content.addComponentAsFirst(new Span("Contact: " + user.getEmail()));
        }
        content.setPadding(false);
        add(content);
    }

    /** Gebruikers met een te laat item of met uitgeschakeld uitlenen, met hun huidige geleende items. */
    public static Map<User, List<LoanedItem>> blackListedUsers(List<User> users, List<Book> books,
                                                               List<Magazine> magazines, List<LKBook> lkBooks,
                                                               Predicate<User> userFilter) {
        Map<User, List<LoanedItem>> loans = new LinkedHashMap<>();
        books.forEach(b -> addItem(loans, b.getGeleendDoor(),
                new LoanedItem("Boek", b.getTitel(), b.getCode(), b.getUitgeleendTot())));
        lkBooks.forEach(b -> addItem(loans, b.getGeleendDoor(),
                new LoanedItem("Leerkrachtenboek", b.getTitel(), b.getCode(), b.getUitgeleendTot())));
        magazines.forEach(m -> addItem(loans, m.getGeleendDoor(),
                new LoanedItem("Tijdschrift", m.getNaamtijdschrift() + " " + (m.getTitel() == null ? "" : m.getTitel()),
                        m.getCode(), m.getUitgeleendTot())));

        Map<User, List<LoanedItem>> result = new LinkedHashMap<>();
        users.stream().filter(u -> Boolean.FALSE.equals(u.getEnabledUI()))
                .forEach(u -> result.put(u, loans.getOrDefault(u, new ArrayList<>())));
        loans.forEach((u, items) -> {
            if (items.stream().anyMatch(LoanedItem::isOverdue)) {
                result.putIfAbsent(u, items);
            }
        });

        result.keySet().removeIf(u -> !userFilter.test(u));
        result.values().forEach(l -> l.sort(Comparator.comparing(LoanedItem::uitgeleendTot,
                Comparator.nullsLast(Comparator.naturalOrder()))));
        return result;
    }

    private static void addItem(Map<User, List<LoanedItem>> map, User user, LoanedItem item) {
        if (user != null && item.uitgeleendTot() != null) {
            map.computeIfAbsent(user, u -> new ArrayList<>()).add(item);
        }
    }
}
