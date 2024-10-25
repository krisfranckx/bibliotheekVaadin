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


    public LK_BlackList(UserService userService, BookService bookService, MagazineService magazineService, LKBookService lkBookService) {
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
        List<Book> bookList = new ArrayList<>();
        List<Magazine> magazineList = new ArrayList<>();
        List<LKBook> lkbookList = new ArrayList<>();
        bookList = bookService.getBooksBorrowed("").getData().getBooksAllWithFilter().stream().filter(b->b.getUitgeleendTot()!=null && b.getUitgeleendTot().isBefore(LocalDate.now())).toList();
        magazineList = magazineService.getMagazines("").getData().getMagazines().stream().filter(b->b.getUitgeleendTot()!=null && b.getUitgeleendTot().isBefore(LocalDate.now())).toList();
        lkbookList = lkbookService.getBooks("").getData().getLKBooksAllWithFilter().stream().filter(b->b.getUitgeleendTot()!=null && b.getUitgeleendTot().isBefore(LocalDate.now())).toList();

        bookList.forEach(book -> {
            if (book.getGeleendDoor().getAuth().contains("LK")){
                userList.add(book.getGeleendDoor());
            }
        });
        magazineList.forEach(magazine -> {
                    if (magazine.getGeleendDoor().getAuth().contains("LK")) {
                        userList.add(magazine.getGeleendDoor());
                    }
                }
        );
        lkbookList.forEach(book -> {
            if (book.getGeleendDoor().getAuth().contains("LK")){
                userList.add(book.getGeleendDoor());
            }
        });
        userList.forEach(u->{
            H6 h6 = new H6(String.format("%s\t\titem(s) geleend",u.getAantalItemsGeleend()));
            Details userDetails = new Details(new H5(u.getVoornaam()+" "+u.getFamilienaam()), h6);
            userDetails.addThemeVariants(DetailsVariant.FILLED);
            add(userDetails);
        });
    }
}
