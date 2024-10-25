package com.example.Vaadin_REST_bib.views.lists.AdminLeraar;


import com.example.Vaadin_REST_bib.restClasses.Response;
import com.example.Vaadin_REST_bib.restClasses.User;
import com.example.Vaadin_REST_bib.services.UserService;
import com.example.Vaadin_REST_bib.views.ADMIN_LK_MainLayout;
import com.example.Vaadin_REST_bib.views.ADMIN_LL_MainLayout;
import com.example.Vaadin_REST_bib.views.forms.UserForm_ChangePasword;
import com.example.Vaadin_REST_bib.views.lists.adminLeerling.BookList;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.security.core.context.SecurityContextHolder;

@PageTitle("Gebruiker | gegevens")
@Route(value = "lk/admin/userdetails", layout = ADMIN_LK_MainLayout.class) //wordt geladen binnen mainlayout
@RolesAllowed("LKADMIN")
public class LK_UserDetails extends VerticalLayout {
    private final UserService userService;
    private UserForm_ChangePasword userFormChangePassword;


    public LK_UserDetails(UserService userService) {
        this.userService = userService;
        addClassName("user_details");
        setSizeFull();  //tot beneden
        configureUserform();
        editContact(SecurityContextHolder.getContext().getAuthentication().getName());

        add(
                userFormChangePassword
        );

    }

    private void configureUserform() {
        userFormChangePassword = new UserForm_ChangePasword();
        userFormChangePassword.setWidth("25em");
        userFormChangePassword.addListener(UserForm_ChangePasword.SaveEvent.class, this::saveUser);
        userFormChangePassword.addListener(UserForm_ChangePasword.CloseEvent.class, e -> closeEditor());
    }

    private void editContact(String email) {
        User user = userService.getUserByEmail(email).getData().getsecurityUser().orElseThrow(RuntimeException::new);
        userFormChangePassword.setUser(user);
        userFormChangePassword.setVisible(true);
    }

    private void closeEditor() {
        //onzichtbare btn
        UI.getCurrent().navigate(LK_admin_BookList.class);

    }

    private void saveUser(UserForm_ChangePasword.SaveEvent event) {
        Response response = userService.saveUser(event.getUser());
        String message = response.getMessage();
        Notification.show(message, 5000, Notification.Position.TOP_CENTER);
        closeEditor();
    }



}
