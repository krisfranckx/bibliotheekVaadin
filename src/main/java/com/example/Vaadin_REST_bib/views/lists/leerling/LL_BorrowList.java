package com.example.Vaadin_REST_bib.views.lists.leerling;


import com.example.Vaadin_REST_bib.restClasses.Book;
import com.example.Vaadin_REST_bib.restClasses.Magazine;
import com.example.Vaadin_REST_bib.restClasses.User;
import com.example.Vaadin_REST_bib.services.BookService;
import com.example.Vaadin_REST_bib.services.MagazineService;
import com.example.Vaadin_REST_bib.services.UserService;
import com.example.Vaadin_REST_bib.views.LLN_MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.H6;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@PageTitle("Geleend | lijst")
@Route(value = "ll/list", layout = LLN_MainLayout.class) //wordt geladen binnen mainlayout
@RolesAllowed("USER")
public class LL_BorrowList extends VerticalLayout {
    private final UserService userService;
    private final BookService bookService;
    private final MagazineService magazineService;
    TextField filterText = new TextField();


    public LL_BorrowList(UserService userService, BookService bookService, MagazineService magazineService) {
        this.userService = userService;
        this.bookService = bookService;
        this.magazineService = magazineService;

        filterText.setPlaceholder("filter op naam");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e->updateDetails());
        add(filterText);
        getToolbar();
        updateDetails();

    }

    private void updateDetails() {
        this.removeAll();
        this.add(filterText);  // dubbel?
        filterText.focus();
        User user = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).getData().getsecurityUser().get();
        List<Book> bookList = bookService.getBooksBorrowed("").getData().getBooksAllWithFilter().stream().filter(b->b.getGeleendDoor()!=null).toList();
        List<Magazine> magazineList = magazineService.getMagazines("").getData().getMagazines().stream().filter(b->b.getGeleendDoor()!=null).toList();
        List<Book> userBookList=new ArrayList<>();
            for (Book book:bookList) {
                if (Objects.equals(book.getGeleendDoor().getEmail(), user.getEmail())){
                    userBookList.add(book);
                }
            }
            List<Magazine> userMagazineList=new ArrayList<>();
            for (Magazine magazine:magazineList) {
                if (Objects.equals(magazine.getGeleendDoor().getId(), user.getId())){
                    userMagazineList.add(magazine);
                }
            }
            VerticalLayout boekenlijst = new VerticalLayout();
            userBookList.forEach(item -> {
                HorizontalLayout horizontalLayout = new HorizontalLayout();
                horizontalLayout.setAlignItems(Alignment.CENTER);

                H6 h6 = new H6(String.format("%s  -  %s --> uitgeleend tot %s",item.getTitel(),item.getAuteur(),item.getUitgeleendTot()));
                if (item.getUitgeleendTot()!= null && item.getUitgeleendTot().isBefore(LocalDate.now())){
                    h6.getStyle().setColor("red");
                }
                horizontalLayout.add(h6);
                boekenlijst.add(horizontalLayout);
            });
            userMagazineList.forEach(item -> {
                HorizontalLayout horizontalLayout = new HorizontalLayout();
                horizontalLayout.setAlignItems(Alignment.CENTER);
                H6 h6 = new H6(String.format("%s  -  %s\t\t%s --> uitgeleend tot %s",item.getTitel(),item.getNummertijdschrift(), item.getJaargang(), item.getUitgeleendTot()));
                if (item.getUitgeleendTot()!= null && item.getUitgeleendTot().isBefore(LocalDate.now())){
                    h6.getStyle().setColor("red");
                }
                horizontalLayout.add(h6);
                boekenlijst.add(horizontalLayout);
            });
            add(boekenlijst);
    }


    private Component getToolbar() {
        filterText.setPlaceholder("filter");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        return filterText;
    }


}
