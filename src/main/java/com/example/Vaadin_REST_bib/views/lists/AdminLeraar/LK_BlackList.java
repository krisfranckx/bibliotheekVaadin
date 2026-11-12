package com.example.Vaadin_REST_bib.views.lists.AdminLeraar;


import com.example.Vaadin_REST_bib.restClasses.Book;
import com.example.Vaadin_REST_bib.restClasses.LKBook;
import com.example.Vaadin_REST_bib.restClasses.Magazine;
import com.example.Vaadin_REST_bib.restClasses.User;
import com.example.Vaadin_REST_bib.services.BookService;
import com.example.Vaadin_REST_bib.services.LKBookService;
import com.example.Vaadin_REST_bib.services.MagazineService;
import com.example.Vaadin_REST_bib.services.UserService;
import com.example.Vaadin_REST_bib.views.ADMIN_LK_MainLayout;
import com.example.Vaadin_REST_bib.views.ADMIN_LL_MainLayout;
import com.example.Vaadin_REST_bib.views.lists.BlackListUserDetails;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.details.DetailsVariant;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.H6;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@PageTitle("Blacklist")
@Route(value = "lk/admin/blacklist", layout = ADMIN_LK_MainLayout.class) //wordt geladen binnen mainlayout
@RolesAllowed("LKADMIN")
public class LK_BlackList extends VerticalLayout {
    private final UserService userService;
    private final BookService bookService;
    private final LKBookService lkbookService;
    private final MagazineService magazineService;

    TextField filterText = new TextField();
    private List<Book> allBooks = new ArrayList<>();
    private List<Magazine> allMagazines = new ArrayList<>();
    private List<LKBook> allLkBooks = new ArrayList<>();
    private List<User> allUsers = new ArrayList<>();


    public LK_BlackList(UserService userService, BookService bookService, MagazineService magazineService, LKBookService lkBookService) {
        this.userService = userService;
        this.bookService = bookService;
        this.magazineService = magazineService;
        this.lkbookService = lkBookService;

        filterText.setPlaceholder("filter op naam");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.setValueChangeTimeout(150);
        filterText.addValueChangeListener(e->updateDetails());
        add(filterText);
        reload();
    }

    private void reload() {
        allBooks = bookService.getBooksBorrowed("").getData().getBooksAllWithFilter();
        allMagazines = magazineService.getMagazines("").getData().getMagazines();
        allLkBooks = lkbookService.getBooks("").getData().getLKBooksAllWithFilter();
        allUsers = userService.getUsers("").getData().getUsers();
        updateDetails();
    }

    private void updateDetails() {
        getChildren().filter(c -> c != filterText).toList().forEach(this::remove);
        List<Book> bookList = allBooks;
        List<Magazine> magazineList = allMagazines;
        List<LKBook> lkbookList = allLkBooks;
        List<User> users = allUsers;

        String filter = filterText.getValue() == null ? "" : filterText.getValue().trim().toLowerCase();
        BlackListUserDetails.blackListedUsers(users, bookList, magazineList, lkbookList,
                u -> u.getAuth() != null && u.getAuth().contains("LK")
                        && (u.getVoornaam() + " " + u.getFamilienaam()).toLowerCase().contains(filter))
                .forEach((u, items) -> add(new BlackListUserDetails(u, items)));
    }
}
