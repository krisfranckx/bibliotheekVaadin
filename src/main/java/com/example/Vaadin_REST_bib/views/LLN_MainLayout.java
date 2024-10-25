package com.example.Vaadin_REST_bib.views;

import com.example.Vaadin_REST_bib.config.SecurityService;
import com.example.Vaadin_REST_bib.services.UserService;
import com.example.Vaadin_REST_bib.views.lists.adminLeerling.UserDetails;
import com.example.Vaadin_REST_bib.views.lists.leerling.LLBookList;
import com.example.Vaadin_REST_bib.views.lists.leerling.LLMagazineList;
import com.example.Vaadin_REST_bib.views.lists.leerling.LL_BorrowList;
import com.example.Vaadin_REST_bib.views.lists.leerling.LL_UserDetails;
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


@RolesAllowed("USER")
@Route("/ll")
public class LLN_MainLayout extends BaseLayout {
    private SecurityService securityService;
    private UserService userService;
    //Dialog dialog = new Dialog();

    public LLN_MainLayout(SecurityService securityService, UserService userService) {
        this.securityService = securityService;
        this.userService = userService;
        createHeader();
        createDrawer();
    }



    private void createDrawer() {

        Icon icon_book = VaadinIcon.BOOK.create();
        RouterLink bookList = new RouterLink("Boeken", LLBookList.class);
        HorizontalLayout hl_book = new HorizontalLayout(icon_book,bookList);

        Icon icon_magazines = VaadinIcon.BOOK_PERCENT.create();
        RouterLink magazineList = new RouterLink("Magazines", LLMagazineList.class);
        HorizontalLayout hl_magazines = new HorizontalLayout(icon_magazines,magazineList);

        RouterLink uitleenDienst = new RouterLink("Mijn lijst", LL_BorrowList.class);


        bookList.setHighlightCondition(HighlightConditions.sameLocation());
        uitleenDienst.setHighlightCondition(HighlightConditions.sameLocation());

        addToDrawer(new VerticalLayout(
                hl_book,
                hl_magazines,
                new Hr(),
                uitleenDienst
        ));
    }

    private void createHeader() {
        H1 logo = new H1("Bibliotheek atheneum Boom");
        logo.addClassNames("text-l","m-m");//margin medium --> vaadin design system

        MenuBar menuBar = new MenuBar();
        menuBar.addThemeVariants(MenuBarVariant.LUMO_END_ALIGNED);
        MenuItem nameUser = menuBar.addItem(new H4(SecurityContextHolder.getContext().getAuthentication().getName()));
        SubMenu nameUserSubMenu = nameUser.getSubMenu();

        nameUserSubMenu.addItem("Logout");
        RouterLink userDetailsRouter = new RouterLink("Verander paswoord", LL_UserDetails.class);
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
