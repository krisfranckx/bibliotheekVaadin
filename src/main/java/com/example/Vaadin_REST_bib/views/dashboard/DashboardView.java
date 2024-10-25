package com.example.Vaadin_REST_bib.views.dashboard;

import com.example.Vaadin_REST_bib.services.BookService;
import com.example.Vaadin_REST_bib.services.MagazineService;
import com.example.Vaadin_REST_bib.services.UserService;
import com.example.Vaadin_REST_bib.views.ADMIN_LL_MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;

@Route(value = "dashboard", layout = ADMIN_LL_MainLayout.class)
@PageTitle("BIB | Dashboard")
@RolesAllowed({"USER", "ADMIN"})
public class DashboardView extends VerticalLayout {
    private BookService bookService;
    private UserService userService;
    public MagazineService magazineService;

    public DashboardView(BookService bookService, UserService userService, MagazineService magazineService) {
        this.bookService = bookService;
        this.magazineService = magazineService;
        this.userService = userService;
        addClassName("dashboard-view");
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        add(getbookStats(), getuserStats(), getMagazineStats() );
    }

    private Component getuserStats() {
        Span stats = new Span(userService.countUsers() + " users");
        stats.addClassNames("text-xl", "mt-m");
        return stats;
    }

    private Component getbookStats() {
        Span stats = new Span(bookService.countBooks() + " boeken");
        stats.addClassNames("text-xl", "mt-m");
        return stats;
    }

    private Component getMagazineStats() {
        Span stats = new Span(magazineService.countMagazines() + " magazines");
        stats.addClassNames("text-xl", "mt-m");
        return stats;
    }

}
