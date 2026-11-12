package com.example.Vaadin_REST_bib.views.dashboard;

import com.example.Vaadin_REST_bib.services.BookService;
import com.example.Vaadin_REST_bib.services.MagazineService;
import com.example.Vaadin_REST_bib.services.UserService;
import com.example.Vaadin_REST_bib.restClasses.Book;
import com.example.Vaadin_REST_bib.restClasses.Magazine;
import com.example.Vaadin_REST_bib.restClasses.Response;
import com.example.Vaadin_REST_bib.restClasses.User;
import com.example.Vaadin_REST_bib.views.ADMIN_LL_MainLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Route(value = "dashboard", layout = ADMIN_LL_MainLayout.class)
@PageTitle("BIB | Dashboard")
@RolesAllowed({"USER", "ADMIN"})
public class DashboardView extends VerticalLayout {
    private final BookService bookService;
    private final UserService userService;
    private final MagazineService magazineService;

    public DashboardView(BookService bookService, UserService userService, MagazineService magazineService) {
        this.bookService = bookService;
        this.magazineService = magazineService;
        this.userService = userService;
        addClassName("dashboard-view");
        setWidthFull();
        setPadding(true);
        setSpacing(true);

        DashboardData dashboardData = loadDashboardData();
        add(new H2("Dashboard"), createStats(dashboardData), createWarnings(dashboardData));
    }

    private Div createStats(DashboardData data) {
        Div stats = new Div();
        stats.addClassName("dashboard-cards");
        stats.add(
                statCard("Boeken", String.valueOf(data.totalBooks()), "in de collectie"),
                statCard("Gebruikers", String.valueOf(data.totalUsers()), "geregistreerd"),
                statCard("Uitgeleend", String.valueOf(data.borrowedCount()),
                        data.borrowedBooks().size() + " boeken · " + data.borrowedMagazines().size() + " tijdschriften"),
                statCard("Te laat", String.valueOf(data.overdueCount()), "terugbrengen vereist"),
                statCard("Binnen 7 dagen", String.valueOf(data.dueSoonCount()), "tijdig opvolgen")
        );
        return stats;
    }

    private Div statCard(String title, String value, String detail) {
        Div card = new Div();
        card.addClassName("dashboard-card");
        Span cardTitle = new Span(title);
        cardTitle.addClassName("dashboard-card-title");
        H2 cardValue = new H2(value);
        cardValue.addClassName("dashboard-card-value");
        Span cardDetail = new Span(detail);
        cardDetail.addClassName("dashboard-card-detail");
        card.add(cardTitle, cardValue, cardDetail);
        return card;
    }

    private Div createWarnings(DashboardData data) {
        Div warnings = new Div();
        warnings.addClassName("dashboard-warnings");
        H3 title = new H3("Aandacht nodig");
        warnings.add(title);

        if (data.warnings().isEmpty()) {
            Span empty = new Span("Geen items met een dringende uitleentermijn.");
            empty.addClassName("dashboard-empty");
            warnings.add(empty);
            return warnings;
        }

        data.warnings().stream()
                .limit(8)
                .map(this::warningRow)
                .forEach(warnings::add);
        return warnings;
    }

    private Div warningRow(LoanWarning warning) {
        Div row = new Div();
        row.addClassName("dashboard-warning");
        row.addClassName(warning.overdue() ? "dashboard-warning-overdue" : "dashboard-warning-soon");

        Span item = new Span(warning.type() + " · " + warning.title()
            + " · " + warning.borrower());
        Span dueDate = new Span(warning.overdue()
                ? "Te laat sinds " + warning.dueDate()
                : "Terugbrengen tegen " + warning.dueDate());
        dueDate.addClassName("dashboard-warning-date");
        row.add(item, dueDate);
        return row;
    }

    private DashboardData loadDashboardData() {
        CompletableFuture<List<Book>> booksFuture = CompletableFuture.supplyAsync(this::loadBorrowedBooks);
        CompletableFuture<List<Magazine>> magazinesFuture = CompletableFuture.supplyAsync(this::loadBorrowedMagazines);
        CompletableFuture<Integer> bookCount = CompletableFuture.supplyAsync(() -> safeCount(() -> bookService.countBooks()));
        CompletableFuture<Integer> userCount = CompletableFuture.supplyAsync(() -> safeCount(() -> userService.countUsers()));
        CompletableFuture<Integer> magazineCount = CompletableFuture.supplyAsync(() -> safeCount(() -> magazineService.countMagazines()));

        List<Book> borrowedBooks = booksFuture.join();
        List<Magazine> borrowedMagazines = magazinesFuture.join();
        List<LoanWarning> warnings = new ArrayList<>();
        LocalDate today = LocalDate.now();
        LocalDate dueSoonLimit = today.plusDays(7);

        borrowedBooks.forEach(book -> addWarning(warnings, "Boek", book.getTitel(), book.getGeleendDoor(),
            book.getUitgeleendTot(), today, dueSoonLimit));
        borrowedMagazines.forEach(magazine -> addWarning(warnings, "Tijdschrift", magazine.getTitel(), magazine.getGeleendDoor(),
            magazine.getUitgeleendTot(), today, dueSoonLimit));
        warnings.sort(Comparator.comparing(LoanWarning::dueDate));

        return new DashboardData(
                bookCount.join(),
                userCount.join(),
                magazineCount.join(),
                borrowedBooks,
                borrowedMagazines,
                warnings
        );
    }

    private List<Book> loadBorrowedBooks() {
        try {
            Response response = bookService.getBooksBorrowed("");
            if (response == null || response.getData() == null || response.getData().getBooksAllWithFilter() == null) {
                return List.of();
            }
            return response.getData().getBooksAllWithFilter().stream()
                    .filter(Book::getUitgeleend)
                    .toList();
        } catch (RuntimeException exception) {
            return List.of();
        }
    }

    private List<Magazine> loadBorrowedMagazines() {
        try {
            Response response = magazineService.getMagazines("");
            if (response == null || response.getData() == null || response.getData().getMagazines() == null) {
                return List.of();
            }
            return response.getData().getMagazines().stream()
                    .filter(Magazine::getUitgeleend)
                    .toList();
        } catch (RuntimeException exception) {
            return List.of();
        }
    }

    private void addWarning(List<LoanWarning> warnings, String type, String title, User borrower, LocalDate dueDate,
                            LocalDate today, LocalDate dueSoonLimit) {
        if (dueDate == null || dueDate.isAfter(dueSoonLimit)) {
            return;
        }
        warnings.add(new LoanWarning(type, title == null ? "Onbekende titel" : title,
                borrowerName(borrower), dueDate, dueDate.isBefore(today)));
    }

    private String borrowerName(User borrower) {
        if (borrower == null) {
            return "Onbekende ontlener";
        }
        String name = ((borrower.getVoornaam() == null ? "" : borrower.getVoornaam()) + " "
                + (borrower.getFamilienaam() == null ? "" : borrower.getFamilienaam())).trim();
        return name.isEmpty() ? "Onbekende ontlener" : name;
    }

    private int safeCount(IntSupplier supplier) {
        try {
            return supplier.getAsInt();
        } catch (RuntimeException exception) {
            return 0;
        }
    }

    @FunctionalInterface
    private interface IntSupplier {
        int getAsInt();
    }

    private record DashboardData(int totalBooks, int totalUsers, int totalMagazines,
                                 List<Book> borrowedBooks, List<Magazine> borrowedMagazines,
                                 List<LoanWarning> warnings) {
        private int borrowedCount() {
            return borrowedBooks.size() + borrowedMagazines.size();
        }

        private int overdueCount() {
            return (int) warnings.stream().filter(LoanWarning::overdue).count();
        }

        private int dueSoonCount() {
            return (int) warnings.stream().filter(warning -> !warning.overdue()).count();
        }
    }

    private record LoanWarning(String type, String title, String borrower, LocalDate dueDate, boolean overdue) {}
}
