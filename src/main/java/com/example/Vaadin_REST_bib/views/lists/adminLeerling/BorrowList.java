package com.example.Vaadin_REST_bib.views.lists.adminLeerling;


import com.example.Vaadin_REST_bib.restClasses.*;
import com.example.Vaadin_REST_bib.services.BookService;
import com.example.Vaadin_REST_bib.services.MagazineService;
import com.example.Vaadin_REST_bib.services.UserService;
import com.example.Vaadin_REST_bib.views.ADMIN_LL_MainLayout;
import com.example.Vaadin_REST_bib.views.lists.BorrowListUserDetails;
import com.example.Vaadin_REST_bib.views.lists.BorrowListUserDetails.BorrowedItem;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@PageTitle("Geleend | lijst")
@Route(value = "ll/admin/list", layout = ADMIN_LL_MainLayout.class) //wordt geladen binnen mainlayout
@RolesAllowed("ADMIN")
public class BorrowList extends VerticalLayout {
    private final UserService userService;
    private final BookService bookService;
    private final MagazineService magazineService;
    TextField filterText = new TextField();


    public BorrowList(UserService userService, BookService bookService, MagazineService magazineService) {
        this.userService = userService;
        this.bookService = bookService;
        this.magazineService = magazineService;

        filterText.setPlaceholder("filter op naam");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e->updateDetails());

        updateDetails();
    }

    private void updateDetails() {
        this.removeAll();
        this.add(filterText);
        filterText.focus();
        List<User> userList = userService.getUsers(filterText.getValue()).getData().getUsers().stream().filter(u->u.getAantalItemsGeleend()>0).toList();
        List<Book> bookList = bookService.getBooksBorrowed("").getData().getBooksAllWithFilter().stream().filter(b->b.getGeleendDoor()!=null).toList();
        List<Magazine> magazineList = magazineService.getMagazines("").getData().getMagazines().stream().filter(b->b.getGeleendDoor()!=null).toList();
        userList.forEach(u->{
            List<BorrowedItem> items = new ArrayList<>();
            for (Book book : bookList) {
                if (Objects.equals(book.getGeleendDoor().getId(), u.getId())) {
                    items.add(new BorrowedItem("Boek", book.getTitel(), book.getAuteur(), book.getCode(), book.getUitgeleendTot(),
                            () -> bringBackBook(book, book.getGeleendDoor()), () -> increaseBorrowBook(book)));
                }
            }
            for (Magazine magazine : magazineList) {
                if (Objects.equals(magazine.getGeleendDoor().getId(), u.getId())) {
                    items.add(new BorrowedItem("Tijdschrift", magazine.getNaamtijdschrift(),
                            String.format("nr %s, jaargang %s", magazine.getNummertijdschrift(), magazine.getJaargang()),
                            magazine.getCode_tijdschrift(), magazine.getUitgeleendTot(),
                            () -> bringBackMagazine(magazine, magazine.getGeleendDoor()), () -> increaseBorrowMagazine(magazine)));
                }
            }
            if (!items.isEmpty()) {
                add(new BorrowListUserDetails(u, items));
            }
        });
    }


    private void increaseBorrowMagazine(Magazine magazine){
        Response response = magazineService.increaseBorrowMagazine(magazine);
        String message = response.getMessage();
        Notification.show(message, 5000, Notification.Position.TOP_CENTER);
        updateDetails();
    }
    private void increaseBorrowBook(Book book){
        Response response = bookService.increaseBorrowBook(book);
        String message = response.getMessage();
        Notification.show(message, 5000, Notification.Position.TOP_CENTER);
        updateDetails();

    }
    private void bringBackBook(Book book, User user){
        Response response = bookService.bringBackBook(book, user);
        String message = response.getMessage();
        Notification.show(message, 5000, Notification.Position.TOP_CENTER);
        updateDetails();
    }
    private void bringBackMagazine(Magazine magazine, User user){
        Response response = magazineService.bringBackMagazine(magazine, user);
        String message = response.getMessage();
        Notification.show(message, 5000, Notification.Position.TOP_CENTER);
        updateDetails();
    }

}
