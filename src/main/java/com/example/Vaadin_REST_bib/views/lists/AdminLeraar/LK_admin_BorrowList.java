package com.example.Vaadin_REST_bib.views.lists.AdminLeraar;


import com.example.Vaadin_REST_bib.restClasses.*;
import com.example.Vaadin_REST_bib.services.BookService;
import com.example.Vaadin_REST_bib.services.LKBookService;
import com.example.Vaadin_REST_bib.services.MagazineService;
import com.example.Vaadin_REST_bib.services.UserService;
import com.example.Vaadin_REST_bib.views.ADMIN_LK_MainLayout;
import com.example.Vaadin_REST_bib.views.ADMIN_LL_MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.details.DetailsVariant;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.H6;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@PageTitle("Geleend | lijst")
@Route(value = "lk/admin/list", layout = ADMIN_LK_MainLayout.class) //wordt geladen binnen mainlayout
@RolesAllowed("LKADMIN")
public class LK_admin_BorrowList extends VerticalLayout {
    private final UserService userService;
    private final LKBookService bookService;
    private final MagazineService magazineService;
    TextField filterText = new TextField();


    public LK_admin_BorrowList(UserService userService, LKBookService bookService, MagazineService magazineService) {
        this.userService = userService;
        this.bookService = bookService;
        this.magazineService = magazineService;

        filterText.setPlaceholder("filter op naam");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e->updateDetails());
        add(filterText);

        updateDetails();

    }

    private void updateDetails() {
        this.removeAll();
        this.add(filterText);  // dubbel?
        filterText.focus();
        List<User> userList = userService.getUsers(filterText.getValue()).getData().getUsers().stream().filter(u->u.getAantalItemsGeleend()>0).filter(u->u.getAuth().contains("LK")).toList();
        List<LKBook> bookList = bookService.getBooks("").getData().getLKBooksAllWithFilter().stream().filter(b->b.getGeleendDoor()!=null).toList();
        List<Magazine> magazineList = magazineService.getMagazines("").getData().getMagazines().stream().filter(b->b.getGeleendDoor()!=null).toList();
        userList.forEach(u->{
            List<LKBook> userBookList=new ArrayList<>();
            for (LKBook book:bookList) {
                if (Objects.equals(book.getGeleendDoor().getId(), u.getId())){
                    userBookList.add(book);
                }
            }
            List<Magazine> userMagazineList=new ArrayList<>();
            for (Magazine magazine:magazineList) {
                if (Objects.equals(magazine.getGeleendDoor().getId(), u.getId())){
                    userMagazineList.add(magazine);
                }
            }
            VerticalLayout boekenlijst = new VerticalLayout();
            userBookList.forEach(item -> {
                HorizontalLayout horizontalLayout = new HorizontalLayout();
                horizontalLayout.setAlignItems(Alignment.CENTER);

                H6 h6 = new H6(String.format("%s\t\t%s   %s",item.getTitel(),item.getAuteur(), item.getCode()));
                if (item.getUitgeleendTot()!= null && item.getUitgeleendTot().isBefore(LocalDate.now())){
                    h6.getStyle().setColor("red");
                }
                Button btn_inleveren = new Button(VaadinIcon.DOWNLOAD_ALT.create());
                btn_inleveren.setTooltipText("Inleveren");
                btn_inleveren.addClickListener(event->bringBackBook(item, item.getGeleendDoor()));
                Button btn_verlengen = new Button(VaadinIcon.FAST_FORWARD.create());
                btn_verlengen.setTooltipText("Verlengen");
                btn_verlengen.addClickListener(event->increaseBorrowBook(item));
                horizontalLayout.add(h6,btn_inleveren, btn_verlengen);
                boekenlijst.add(horizontalLayout);
            });
            userMagazineList.forEach(item -> {
                HorizontalLayout horizontalLayout = new HorizontalLayout();
                horizontalLayout.setAlignItems(Alignment.CENTER);
                H6 h6 = new H6(String.format("%s\t\t%s\t\t%s  %s",item.getTitel(),item.getNummertijdschrift(), item.getJaargang(), item.getCode()));
                if (item.getUitgeleendTot()!= null && item.getUitgeleendTot().isBefore(LocalDate.now())){
                    h6.getStyle().setColor("red");
                }
                Button btn_inleveren = new Button(VaadinIcon.DOWNLOAD_ALT.create());
                btn_inleveren.addClickListener(event->bringBackMagazine(item, item.getGeleendDoor()));
                Button btn_verlengen = new Button(VaadinIcon.FAST_FORWARD.create());
                btn_verlengen.setTooltipText("Verlengen");
                btn_verlengen.addClickListener(event->increaseBorrowMagazine(item));
                horizontalLayout.add(h6,btn_inleveren, btn_verlengen);
                boekenlijst.add(horizontalLayout);
            });
            Details userDetails = new Details(new H5(u.getVoornaam()+" "+u.getFamilienaam()),boekenlijst);

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


    private void increaseBorrowMagazine(Magazine magazine){
        Response response = magazineService.increaseBorrowMagazine(magazine);
        String message = response.getMessage();
        Notification.show(message, 5000, Notification.Position.TOP_CENTER);
    }
    private void increaseBorrowBook(LKBook book){
        Response response = bookService.increaseBorrowBook(book);
        String message = response.getMessage();
        Notification.show(message, 5000, Notification.Position.TOP_CENTER);
        updateDetails();

    }
    private void bringBackBook(LKBook book, User user){
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
