package com.example.Vaadin_REST_bib.views;

import com.example.Vaadin_REST_bib.config.SecurityService;
import com.example.Vaadin_REST_bib.services.UserService;
import com.example.Vaadin_REST_bib.views.lists.AdminLeraar.*;
import com.example.Vaadin_REST_bib.views.lists.adminLeerling.UserDetails;
import com.example.Vaadin_REST_bib.views.lists.leraar.LK_BorrowList;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.PWA;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.security.core.context.SecurityContextHolder;


@RolesAllowed("LKADMIN")
@Route("/lk/admin")
public class ADMIN_LK_MainLayout extends BaseLayout {
    private SecurityService securityService;
    private UserService userService;
    //Dialog dialog = new Dialog();

    public ADMIN_LK_MainLayout(SecurityService securityService, UserService userService) {
        this.securityService = securityService;
        this.userService = userService;
        createHeader();
        createDrawer();
    }



    private void createDrawer() {

        Icon icon_book = VaadinIcon.BOOK.create();
        RouterLink bookList = new RouterLink("Boeken", LK_admin_BookList.class);
        HorizontalLayout hl_book = new HorizontalLayout(icon_book,bookList);

      /*  Icon icon_magazines = VaadinIcon.BOOK_PERCENT.create();
        RouterLink magazineList = new RouterLink("Magazines", MagazineList.class);
        HorizontalLayout hl_magazines = new HorizontalLayout(icon_magazines,magazineList);*/

        Icon icon_user = VaadinIcon.USER.create();
        RouterLink userList = new RouterLink("Users", LK_UserList.class);
        HorizontalLayout hl_user = new HorizontalLayout(icon_user,userList);

        RouterLink uitleenDienst = new RouterLink("Uitleendienst", LK_BorrowServiceView.class);
        RouterLink leenLijst = new RouterLink("Leenlijst", LK_admin_BorrowList.class);
        RouterLink blackList = new RouterLink("BlackList", LK_BlackList.class);

        bookList.setHighlightCondition(HighlightConditions.sameLocation());
        userList.setHighlightCondition(HighlightConditions.sameLocation());
        uitleenDienst.setHighlightCondition(HighlightConditions.sameLocation());

        addToDrawer(new VerticalLayout(
                hl_user,
                hl_book,
                new Hr(),
                uitleenDienst,
                leenLijst,
                blackList
        ));
    }

    private void createHeader() {
        H1 logo = new H1("Bibliotheek atheneum Boom - Admin Leraar");
        logo.addClassNames("text-l","m-m");//margin medium --> vaadin design system
        MenuBar menuBar = new MenuBar();
        menuBar.addThemeVariants(MenuBarVariant.LUMO_END_ALIGNED);
        MenuItem nameUser = menuBar.addItem(new H4(SecurityContextHolder.getContext().getAuthentication().getName()));
        SubMenu nameUserSubMenu = nameUser.getSubMenu();
        nameUserSubMenu.addItem("Logout");
        RouterLink userDetailsRouter = new RouterLink("Verander paswoord", LK_UserDetails.class);
        userDetailsRouter.getStyle().setTextDecoration(nameUserSubMenu.getItems().get(0).getStyle().get("color"));
        userDetailsRouter.getStyle().setColor("darkgrey");
        nameUserSubMenu.addItem(userDetailsRouter);
        nameUserSubMenu.getItems().get(0).addClickListener(clickEvent->securityService.logout());
        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), logo, menuBar);
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.expand(logo);
        header.setWidthFull();
        header.addClassNames("py-0","px-m");
        addToNavbar(header);
    }
}
