package com.example.Vaadin_REST_bib.views.lists.adminLeerling;


import com.example.Vaadin_REST_bib.restClasses.Book;
import com.example.Vaadin_REST_bib.restClasses.LKBook;
import com.example.Vaadin_REST_bib.restClasses.Magazine;
import com.example.Vaadin_REST_bib.restClasses.User;
import com.example.Vaadin_REST_bib.services.BookService;
import com.example.Vaadin_REST_bib.services.LKBookService;
import com.example.Vaadin_REST_bib.services.MagazineService;
import com.example.Vaadin_REST_bib.services.UserService;
import com.example.Vaadin_REST_bib.views.ADMIN_LL_MainLayout;
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
import java.util.*;

@PageTitle("Blacklist")
@Route(value = "ll/admin/blacklist", layout = ADMIN_LL_MainLayout.class) //wordt geladen binnen mainlayout
@RolesAllowed("ADMIN")
public class BlackList extends VerticalLayout {
    private final UserService userService;
    private final BookService bookService;
    private final LKBookService lkbookService;
    private final MagazineService magazineService;

    TextField filterText = new TextField();


    public BlackList(UserService userService, BookService bookService, MagazineService magazineService, LKBookService lkBookService) {
        this.userService = userService;
        this.bookService = bookService;
        this.magazineService = magazineService;
        this.lkbookService = lkBookService;

        filterText.setPlaceholder("filter op naam");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e->updateDetails());
        updateDetails();
    }

    private void updateDetails() {
        //accordion is misschien beter om maar 1 tegelijk te openen?
        this.removeAll();
        this.add(filterText);
        filterText.focus();

        Set<User> userList = new HashSet<>();
        List<Book> bookList = bookService.getBooksBorrowed("").getData().getBooksAllWithFilter().stream().filter(b->b.getUitgeleendTot()!=null && b.getUitgeleendTot().isBefore(LocalDate.now())).toList();
        List<Magazine> magazineList = magazineService.getMagazines("").getData().getMagazines().stream().filter(b->b.getUitgeleendTot()!=null && b.getUitgeleendTot().isBefore(LocalDate.now())).toList();
        List<LKBook> lkbookList = lkbookService.getBooks("").getData().getLKBooksAllWithFilter().stream().filter(b->b.getUitgeleendTot()!=null && b.getUitgeleendTot().isBefore(LocalDate.now())).toList();

        bookList.forEach(book -> userList.add(book.getGeleendDoor()));
        magazineList.forEach(magazine -> userList.add(magazine.getGeleendDoor()));
        lkbookList.forEach(book -> userList.add(book.getGeleendDoor()));
        userList.forEach(u->{
            H6 h6 = new H6(String.format("%s\t\t%s geleend",u.getAantalItemsGeleend(), u.getAantalItemsGeleend()>1?"items":"item"));
            Details userDetails = new Details(new H5(u.getVoornaam()+" "+u.getFamilienaam()), h6);
            userDetails.addThemeVariants(DetailsVariant.FILLED);
            add(userDetails);
        });
    }


    private void updateGrid() {
    }




    private Component getToolbar() {
        filterText.setPlaceholder("filter");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e->updateGrid());
        return filterText;
    }



}
