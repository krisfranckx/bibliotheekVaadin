package com.example.Vaadin_REST_bib.views.lists.adminLeerling;

import com.example.Vaadin_REST_bib.restClasses.*;
import com.example.Vaadin_REST_bib.services.BookService;
import com.example.Vaadin_REST_bib.services.MagazineService;
import com.example.Vaadin_REST_bib.services.UserService;
import com.example.Vaadin_REST_bib.views.ADMIN_LL_MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.shared.Tooltip;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.data.provider.SortOrder;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.function.SerializableBiConsumer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@PageTitle("Uitleendienst")
@Route(value = "ll/admin/uitleendienst", layout = ADMIN_LL_MainLayout.class) //wordt geladen binnen mainlayout
@RolesAllowed("ADMIN")
public class BorrowServiceView extends VerticalLayout {
    private final BookService bookService;
    private final UserService userService;
    private final MagazineService magazineService;
    VerticalLayout vl_items = new VerticalLayout();
    VerticalLayout vl_users = new VerticalLayout();
    SplitLayout splitLayout = new SplitLayout();
    Grid<User> userGrid = new Grid<>(User.class);
    Grid<Book> bookGrid = new Grid<>(Book.class);
    Grid<Magazine> magazineGrid = new Grid<>(Magazine.class);

    TextField filterItem = new TextField();
    TextField filterUser = new TextField();
    Button btn_uitlenen = new Button("Uitlenen");
    Button btn_reserveren = new Button("Reserveren");
    Button btn_inleveren = new Button("Inleveren");
    Button btn_vrijgeven = new Button("Vrijgeven");

    ComboBox item;



    public BorrowServiceView(BookService bookService, UserService userService, MagazineService magazineService) {
        this.bookService = bookService;
        this.userService = userService;
        this.magazineService = magazineService;
        setSizeFull();

        configureUserGrid();
        configureItemGrid();

        splitLayout.addToPrimary(vl_items);
        splitLayout.addToSecondary(vl_users);
        splitLayout.setSizeFull();
        splitLayout.setSplitterPosition(70);

        btn_uitlenen.setWidthFull();
        btn_uitlenen.setVisible(false);
        btn_uitlenen.addClickListener((event)->borrowItem());
        btn_reserveren.setWidthFull();
        btn_reserveren.setVisible(false);
        btn_reserveren.addClickListener((event)->reserveItem());
        btn_inleveren.setWidthFull();
        btn_inleveren.setVisible(false);
        btn_inleveren.addClickListener((event)->bringBackItem());
        btn_vrijgeven.setWidthFull();
        btn_vrijgeven.setVisible(false);
        btn_vrijgeven.addClickListener(event->vrijgeven());
        HorizontalLayout hl_btns = new HorizontalLayout(btn_uitlenen, btn_reserveren, btn_inleveren, btn_vrijgeven);
        add(splitLayout,hl_btns);
    }

    private void vrijgeven() {
        if (bookGrid.asSingleSelect().getValue()!=null && bookGrid.asSingleSelect().getValue().getClass()== Book.class){
            Response response = bookService.bookvrijgeven(bookGrid.asSingleSelect().getValue());
            String message = response.getMessage();
            Notification.show(message, 5000, Notification.Position.TOP_CENTER);
            updateGridBooks();
            updateGridUsers();
        }
        if (magazineGrid.asSingleSelect().getValue()!=null && magazineGrid.asSingleSelect().getValue().getClass()== Magazine.class){
            Response response = magazineService.magazineVrijgeven(magazineGrid.asSingleSelect().getValue());
            String message = response.getMessage();
            Notification.show(message, 5000, Notification.Position.TOP_CENTER);
            updateGridMagazines();
            updateGridUsers();
        }
    }
    private void reserveItem() {
        if (bookGrid.asSingleSelect().getValue()!=null && bookGrid.asSingleSelect().getValue().getClass()== Book.class){
            Response response = bookService.reserveBook(bookGrid.asSingleSelect().getValue(), userGrid.asSingleSelect().getValue());
            String message = response.getMessage();
            Notification.show(message, 5000, Notification.Position.TOP_CENTER);
            updateGridBooks();
            updateGridUsers();
        }
        if (magazineGrid.asSingleSelect().getValue()!=null && magazineGrid.asSingleSelect().getValue().getClass()== Magazine.class){
            Response response = magazineService.reserveMagazine(magazineGrid.asSingleSelect().getValue(), userGrid.asSingleSelect().getValue());
            String message = response.getMessage();
            Notification.show(message, 5000, Notification.Position.TOP_CENTER);
            updateGridMagazines();
            updateGridUsers();
        }
    }
    private void borrowItem() {
        if (bookGrid.asSingleSelect().getValue()!=null && bookGrid.asSingleSelect().getValue().getClass()== Book.class && userGrid.asSingleSelect().getValue()!=null){
            Book book = bookGrid.asSingleSelect().getValue();
            Response response = bookService.borrowBook(book, userGrid.asSingleSelect().getValue());
            String message = response.getMessage();
            Notification.show(message, 5000, Notification.Position.TOP_CENTER);
            updateGridBooks();
            updateGridUsers();
        } else if (bookGrid.asSingleSelect().getValue()!=null && bookGrid.asSingleSelect().getValue().getClass()== Book.class && userGrid.asSingleSelect().getValue()==null) {
            Book book = bookGrid.asSingleSelect().getValue();
            Response response = bookService.borrowBook(book, book.getGereserveerdDoor());
            String message = response.getMessage();
            Notification.show(message, 5000, Notification.Position.TOP_CENTER);
            updateGridBooks();
            updateGridUsers();
        }
        if (magazineGrid.asSingleSelect().getValue()!=null && magazineGrid.asSingleSelect().getValue().getClass()== Magazine.class && userGrid.asSingleSelect().getValue()!=null){
            Magazine magazine = magazineGrid.asSingleSelect().getValue();
            Response response = magazineService.borrowMagazine(magazine, userGrid.asSingleSelect().getValue());
            String message = response.getMessage();
            Notification.show(message, 5000, Notification.Position.TOP_CENTER);
            updateGridMagazines();
            updateGridUsers();
        } else if (magazineGrid.asSingleSelect().getValue()!=null && magazineGrid.asSingleSelect().getValue().getClass()== Magazine.class && userGrid.asSingleSelect().getValue()==null) {
            Magazine magazine = magazineGrid.asSingleSelect().getValue();
            Response response = magazineService.borrowMagazine(magazine, magazine.getGereserveerdDoor());
            String message = response.getMessage();
            Notification.show(message, 5000, Notification.Position.TOP_CENTER);
            updateGridMagazines();
            updateGridUsers();
        }

    }
    private void bringBackItem() {
        if (bookGrid.asSingleSelect().getValue()!=null && bookGrid.asSingleSelect().getValue().getClass()== Book.class){
            Book book = bookService.getBook(bookGrid.asSingleSelect().getValue().getBoekId()).getData().getBook();
            User user = userService.getUser(book.getGeleendDoor().getId()).getData().getUser();
            Response response = bookService.bringBackBook(book, user);
            String message = response.getMessage();
            Notification.show(message, 5000, Notification.Position.TOP_CENTER);
            updateGridBooks();
            updateGridUsers();
        }
        if (magazineGrid.asSingleSelect().getValue()!=null && magazineGrid.asSingleSelect().getValue().getClass()== Magazine.class){
            Magazine magazine = magazineService.getMagazine(magazineGrid.asSingleSelect().getValue().getMagazineId()).getData().getMagazine();
            User user = userService.getUser(magazine.getGeleendDoor().getId()).getData().getUser();
            Response response = magazineService.bringBackMagazine(magazine,user);
            String message = response.getMessage();
            Notification.show(message, 5000, Notification.Position.TOP_CENTER);
            updateGridMagazines();
            updateGridUsers();
        }
    }
    private void configureUserGrid() {
        checkUsers();
        userGrid.addClassName("user-grid");
        userGrid.setSizeFull();
        userGrid.setColumns("voornaam", "familienaam");
        userGrid.getColumns().forEach(col->col.setAutoWidth(true));
        userGrid.addColumn(createStatusComponentRenderer_user()).setHeader("").setAutoWidth(true);
        userGrid.setItems(userService.getUsers("").getData().getUsers());
        userGrid.asSingleSelect().addValueChangeListener(e->checkBtnVisible());
        filterUser.setPlaceholder("filter user");
        filterUser.setClearButtonVisible(true);
        filterUser.setValueChangeMode(ValueChangeMode.LAZY);
        filterUser.addValueChangeListener(e->updateGridUsers());
        vl_users.add(filterUser,userGrid);
    }
    private void checkUsers() {
        Set<User> userList = new HashSet<>();
        List<Book> bookList = bookService.getBooksBorrowed("").getData().getBooksAllWithFilter().stream().filter(b->b.getUitgeleendTot()!=null && b.getUitgeleendTot().isBefore(LocalDate.now())).toList();
        List<Magazine> magazineList = magazineService.getMagazines("").getData().getMagazines().stream().filter(b->b.getUitgeleendTot()!=null && b.getUitgeleendTot().isBefore(LocalDate.now())).toList();
        bookList.stream().forEach(book -> userList.add(book.getGeleendDoor()));
        magazineList.stream().forEach(magazine -> userList.add(magazine.getGeleendDoor()));
        userList.forEach(u->{
            u.setEnabledUI(false);
            userService.saveUser(u);
        });
    }
    private void configureItemGrid() {
        item = new ComboBox<String>("","Boeken","Magazines");
        item.setAllowCustomValue(false);
        item.setValue("Boeken");
        HorizontalLayout toolbar = new HorizontalLayout(item, filterItem);
        toolbar.setAlignItems(Alignment.END);
        //bookGrid.setItems(bookService.getBooks("").getData().getBooks());
        bookGrid.setItems(query -> {
                    String sort = query.getSortOrders().size()>0?query.getSortOrders().stream().map(SortOrder::getSorted).findFirst().get():"auteur";
                    SortDirection direction = query.getSortOrders().size()>0?query.getSortOrders().stream().map(SortOrder::getDirection).findFirst().get():SortDirection.ASCENDING;
                    System.out.println("Sort: "+sort+"   direction:"+direction +"    filter: "+filterItem.getValue());
                    return bookService.findAllDataprovider(query.getPageSize(), query.getPage(), filterItem.getValue(), sort, direction).toStream();
                }
        );

        bookGrid.addClassName("book-grid");
        bookGrid.setSizeFull();
        bookGrid.setColumns("titel", "auteur");
        bookGrid.addColumn(Book::getCode).setHeader("Code").setSortable(false);
        bookGrid.addColumn(createStatusComponentRenderer()).setHeader("Status");
        bookGrid.getColumns().forEach(col->col.setAutoWidth(true));
        bookGrid.asSingleSelect().addValueChangeListener(e->checkBtnVisible());
        magazineGrid.setItems(magazineService.getMagazines("").getData().getMagazines());
        magazineGrid.addClassName("magazine-grid");
        magazineGrid.setSizeFull();
        magazineGrid.setColumns();
        magazineGrid.addColumn(Magazine::getNaamtijdschrift).setHeader("Titel").setSortable(true);
        magazineGrid.addColumn(Magazine::getJaargang).setHeader("Jaar").setSortable(true);

        magazineGrid.addColumn(Magazine::getNummertijdschrift).setHeader("nummer").setSortable(true);
        magazineGrid.addColumn(createMagazineComponentRenderer()).setHeader("Status");
        magazineGrid.getColumns().forEach(col->col.setAutoWidth(true));
        magazineGrid.asSingleSelect().addValueChangeListener(e->checkBtnVisible());
        vl_items.add(toolbar, magazineGrid, bookGrid);
        item.addValueChangeListener(event->changeGridEraseFilter());
        magazineGrid.setVisible(false);
        filterItem.setPlaceholder("filter boek");
        filterItem.setClearButtonVisible(true);
        filterItem.setValueChangeMode(ValueChangeMode.LAZY);
        filterItem.addValueChangeListener(e-> {
            if (item.getValue().toString().equalsIgnoreCase("boeken")){
                updateGridBooks();
            }
            if (item.getValue().toString().equalsIgnoreCase("magazines")){
                updateGridMagazines();
            }
        }
        );
    }
    private void changeGridEraseFilter() {
        filterItem.setValue("");
        changeGrid();
    }
    private void changeGrid() {
        if (item.getValue().toString().equalsIgnoreCase("boeken")){
            magazineGrid.asSingleSelect().clear();
            bookGrid.asSingleSelect().clear();
            magazineGrid.setVisible(false);
            bookGrid.setVisible(true);
            filterItem.setPlaceholder("filter boek");
            filterItem.addValueChangeListener(e->updateGridBooks());
            updateGridBooks();
        }
        if (item.getValue().toString().equalsIgnoreCase("magazines")){
            bookGrid.asSingleSelect().clear();
            magazineGrid.asSingleSelect().clear();
            bookGrid.setVisible(false);
            magazineGrid.setVisible(true);
            filterItem.setPlaceholder("filter magazine");
            filterItem.addValueChangeListener(e->updateGridMagazines());
            updateGridMagazines();
        }
    }
    private void checkBtnVisible() {
        btn_uitlenen.setText("Uitlenen");
        if (userGrid.asSingleSelect().getValue()==null || (bookGrid.asSingleSelect().getValue()==null && magazineGrid.asSingleSelect().getValue()==null)){
            btn_uitlenen.setVisible(false);
            btn_reserveren.setVisible(false);
            btn_inleveren.setVisible(false);
            btn_vrijgeven.setVisible(false);
        } else{
            if ((bookGrid.asSingleSelect().getValue()!=null
                    && !bookGrid.asSingleSelect().getValue().getUitgeleend()
                    && userGrid.asSingleSelect().getValue().getEnabledUI()
                    && bookGrid.asSingleSelect().getValue().getGereserveerdDoor()==null
                    )
                    ||
                    (magazineGrid.asSingleSelect().getValue()!=null
                            && !magazineGrid.asSingleSelect().getValue().getUitgeleend()
                            && userGrid.asSingleSelect().getValue().getEnabledUI()
                            && magazineGrid.asSingleSelect().getValue().getGereserveerdDoor()==null
                    )
            ){
                btn_uitlenen.setVisible(true);
                btn_reserveren.setVisible(false);
                btn_inleveren.setVisible(false);
                btn_vrijgeven.setVisible(false);
            }else {
                if ((bookGrid.asSingleSelect().getValue()!=null
                        && bookGrid.asSingleSelect().getValue().getUitgeleend()
                        && userGrid.asSingleSelect().getValue().getEnabledUI()
                        && bookGrid.asSingleSelect().getValue().getGereserveerdDoor()==null)
                        ||
                        (magazineGrid.asSingleSelect().getValue()!=null
                                && magazineGrid.asSingleSelect().getValue().getUitgeleend()
                                && userGrid.asSingleSelect().getValue().getEnabledUI()
                                && magazineGrid.asSingleSelect().getValue().getGereserveerdDoor()==null)
                ){
                    btn_uitlenen.setVisible(false);
                    btn_reserveren.setVisible(true);
                    btn_inleveren.setVisible(false);
                    btn_vrijgeven.setVisible(false);

                } else{
                    btn_uitlenen.setVisible(false);
                    btn_reserveren.setVisible(false);
                    btn_inleveren.setVisible(false);
                    btn_vrijgeven.setVisible(false);
                }
            }
        }
        if (userGrid.asSingleSelect().getValue()==null
                && ((bookGrid.asSingleSelect().getValue()!=null && bookGrid.asSingleSelect().getValue().getUitgeleend())
                    ||
                (magazineGrid.asSingleSelect().getValue()!=null && magazineGrid.asSingleSelect().getValue().getUitgeleend()))
        ) {
            btn_inleveren.setVisible(true);
            btn_uitlenen.setVisible(false);
            btn_reserveren.setVisible(false);
            btn_vrijgeven.setVisible(false);
        }else {
            if (userGrid.asSingleSelect().getValue()==null
                    && ((bookGrid.asSingleSelect().getValue()!=null && bookGrid.asSingleSelect().getValue().getGereserveerdDoor()!=null)
                    ||
                    (magazineGrid.asSingleSelect().getValue()!=null && magazineGrid.asSingleSelect().getValue().getGereserveerdDoor()!=null))
            ) {
                //userGrid.setItems(magazineGrid.asSingleSelect().getValue().getGereserveerdDoor());
                btn_uitlenen.setText(String.format("Uitlenen aan %s",magazineGrid.asSingleSelect().getValue()!=null?magazineGrid.asSingleSelect().getValue().getGereserveerdDoor().getVoornaam():bookGrid.asSingleSelect().getValue().getGereserveerdDoor().getVoornaam()));
                btn_uitlenen.setVisible(true);
                btn_vrijgeven.setVisible(true);
                btn_inleveren.setVisible(false);
                btn_reserveren.setVisible(false);
            }
        }

        }
    private void updateGridBooks() {
        bookGrid.setItems(query -> {
            String sort = query.getSortOrders().size()>0?query.getSortOrders().stream().map(SortOrder::getSorted).findFirst().get():"auteur";
            SortDirection direction = query.getSortOrders().size()>0?query.getSortOrders().stream().map(SortOrder::getDirection).findFirst().get():SortDirection.ASCENDING;
            System.out.println(sort+" ________ "+direction+"   ___ filter: "+filterItem.getValue());
            return bookService.findAllDataprovider(query.getPageSize(), query.getPage(), filterItem.getValue(), sort, direction).toStream();
        });
    }
    private void updateGridMagazines() {
        magazineGrid.setItems(magazineService.getMagazines(filterItem.getValue()).getData().getMagazines());
    }
    private void updateGridUsers() {
        userGrid.setItems(userService.getUsers(filterUser.getValue()).getData().getUsers());
    }
    private static final SerializableBiConsumer<Span, Book> statusComponentUpdater = (
            span, book) -> {
        boolean isUitgeleend = book.getUitgeleend();
        boolean isReserved = book.getGereserveerdDoor()!=null;
        String theme = String.format("badge %s",
                isUitgeleend&&isReserved ? "error" : (isReserved?"default":(isUitgeleend?"contrast":"success")));
        span.getElement().setAttribute("theme", theme);
        span.setText(isUitgeleend&&isReserved?"Uitg en gereserv":(isReserved?"Gereserveerd":(isUitgeleend?"Uitgeleend":"Beschikbaar")));
        Tooltip.forComponent(span).withText(isReserved && isUitgeleend?String.format(
                        "Uitgeleend aan %s %s tot %s \n en gereserveerd door %s %s tot %s",book.getGeleendDoor().getFamilienaam(),book.getGeleendDoor().getVoornaam(),book.getUitgeleendTot(),book.getGereserveerdDoor().getFamilienaam(), book.getGereserveerdDoor().getVoornaam(),book.getGereserveerdTot())
                        :isUitgeleend?String.format("Uitgeleend aan %s %s",book.getGeleendDoor().getFamilienaam(),book.getGeleendDoor().getVoornaam()):isReserved?"Gereserveerd door "+book.getGereserveerdDoor().getFamilienaam()+" "+book.getGereserveerdDoor().getVoornaam():"")
                .withPosition(Tooltip.TooltipPosition.BOTTOM);
    };
    private static ComponentRenderer<Span, Book> createStatusComponentRenderer() {
        return new ComponentRenderer<>(Span::new, statusComponentUpdater);
    }
    private static final SerializableBiConsumer<Span, User> statusComponentUpdater_user = (
            span, user) -> {
        if (!user.getEnabledUI()){
            span.getElement().setAttribute("theme", "badge error");
            span.add(VaadinIcon.CLOSE_CIRCLE.create());
            Tooltip.forComponent(span).withText("Deze user heeft reeds 3 items geleend, " +
                    "\nheeft iets te laat binnengeleverd" +
                    "\n of heeft zijn e-mailadres nog niet geactiveerd.").withPosition(Tooltip.TooltipPosition.BOTTOM);

        }

    };
    private static ComponentRenderer<Span, User> createStatusComponentRenderer_user() {
        return new ComponentRenderer<>(Span::new, statusComponentUpdater_user);
    }
    private static final SerializableBiConsumer<Span, Magazine> magazineComponentUpdater = (
            span, magazine) -> {
        boolean isUitgeleend = magazine.getUitgeleend();
        boolean isReserved = magazine.getGereserveerdDoor()!=null;
        String theme = String.format("badge %s",
                isUitgeleend&&isReserved ? "error" : (isReserved?"default":(isUitgeleend?"contrast":"success")));
        span.getElement().setAttribute("theme", theme);
        span.setText(isUitgeleend&&isReserved?"Uitg en gereserv":(isReserved?"Gereserveerd":(isUitgeleend?"Uitgeleend":"Beschikbaar")));
        Tooltip.forComponent(span).withText(isReserved&&isUitgeleend?String.format(
                        "Uitgeleend aan %s %s tot %s \n en gereserveerd door %s %s tot %s",magazine.getGeleendDoor().getVoornaam(), magazine.getGeleendDoor().getFamilienaam(),magazine.getUitgeleendTot(),magazine.getGereserveerdDoor().getVoornaam(),magazine.getGereserveerdDoor().getFamilienaam(),magazine.getGereserveerdTot())
                        :isUitgeleend?String.format("Uitgeleend aan %s %s",magazine.getGeleendDoor().getVoornaam(), magazine.getGeleendDoor().getFamilienaam()):isReserved?"Gereserveerd door "+magazine.getGereserveerdDoor().getVoornaam():"")
                .withPosition(Tooltip.TooltipPosition.BOTTOM);
    };
    private static ComponentRenderer<Span, Magazine> createMagazineComponentRenderer() {
        return new ComponentRenderer<>(Span::new, magazineComponentUpdater);
    }

}
