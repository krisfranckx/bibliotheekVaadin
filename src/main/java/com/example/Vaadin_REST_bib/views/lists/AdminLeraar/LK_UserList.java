package com.example.Vaadin_REST_bib.views.lists.AdminLeraar;


import com.example.Vaadin_REST_bib.restClasses.Response;
import com.example.Vaadin_REST_bib.restClasses.User;
import com.example.Vaadin_REST_bib.services.UserService;
import com.example.Vaadin_REST_bib.views.ADMIN_LK_MainLayout;
import com.example.Vaadin_REST_bib.views.ADMIN_LL_MainLayout;
import com.example.Vaadin_REST_bib.views.forms.UserForm;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.function.SerializableBiConsumer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;

@PageTitle("Users | lijst")
@Route(value = "lk/admin/users", layout = ADMIN_LK_MainLayout.class)
@RolesAllowed("LKADMIN")
public class LK_UserList extends VerticalLayout {
    private final UserService userService;
    Grid<User> grid = new Grid<User>(User.class);
    TextField filterText = new TextField();
    UserForm userForm;


    public LK_UserList(UserService userService) {
        this.userService = userService;
        addClassName("user-list");
        setSizeFull();  //tot beneden
        configureGrid();
        configureUserform();

        add(
                getToolbar(),
                getInhoud()
        );

        updateGrid();
        closeEditor();
    }

    private void closeEditor() {
        userForm.setUser(null);
        userForm.setVisible(false);
    }

    private void updateGrid() {
        grid.setItems(userService.getUsers(filterText.getValue()).getData().getUsers().stream().filter(u->u.getAuth().contains("LK")).toList());
    }

    private Component getInhoud() {
        HorizontalLayout inhoud = new HorizontalLayout(grid, userForm);
        inhoud.setFlexGrow(2,grid);
        inhoud.setFlexGrow(1, userForm);
        inhoud.addClassName("inhoud");
        inhoud.setSizeFull();
        return inhoud;
    }

    private void configureUserform() {
        userForm = new UserForm();
        userForm.setWidth("25em");

        userForm.addListener(UserForm.SaveEvent.class,this::saveUser);
        userForm.addListener(UserForm.DeleteEvent.class,this::deleteUser);
        userForm.addListener(UserForm.CloseEvent.class, e->closeEditor());
    }

    private void saveUser(UserForm.SaveEvent event) {
        Response response = userService.saveUser(event.getUser());
        String message = response.getMessage();
        Notification.show(message, 5000, Notification.Position.TOP_CENTER);
        updateGrid();
        closeEditor();
    }
    private void deleteUser(UserForm.DeleteEvent event) {
        Response response = userService.deleteUser(event.getUser());
        String message = response.getMessage();
        Notification.show(message, 5000, Notification.Position.TOP_CENTER);
        updateGrid();
        closeEditor();
    }

    private Component getToolbar() {
        filterText.setPlaceholder("filter");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e->updateGrid());

        Button btn_userToevoegen = new Button("User toevoegen");
        btn_userToevoegen.setEnabled(true);
        btn_userToevoegen.addClickListener(e-> addUser());
        HorizontalLayout toolbar = new HorizontalLayout(filterText, btn_userToevoegen);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void addUser() {
        grid.asSingleSelect().clear();
        editContact(new User());
    }

    private void configureGrid() {
        grid.addClassName("user-grid");
        grid.setSizeFull();
        grid.setColumns("voornaam", "familienaam","email");
        grid.addColumn(createAuthComponentRenderer()).setHeader("Rol");
        grid.addColumn(User::getAantalItemsGeleend).setHeader("Aantal geleend").setSortable(true);
        grid.getColumns().forEach(col->col.setAutoWidth(true));
        grid.asSingleSelect().addValueChangeListener(e->editContact(e.getValue()));
    }

    private void editContact(User user) {
        if (user == null){
            closeEditor();
        }else{
            userForm.setUser(user);
            userForm.setVisible(true);
        }
    }

    private static final SerializableBiConsumer<Span, User> authComponentUpdater = (
            span, user) -> {
        String role = user.getAuth();
        switch (role){
            case "ROLE_USER":
                span.getElement().setAttribute("theme","badge success");
                span.setText("LEERLING");
                break;
            case "ROLE_LK":
                span.getElement().setAttribute("theme","badge");
                span.setText("LERAAR");
                break;
            case "ROLE_ADMIN":
                span.getElement().setAttribute("theme","badge error");
                span.setText("LLN_ADMIN");
                break;
            case "ROLE_LKADMIN":
                span.getElement().setAttribute("theme","badge contrast");
                span.setText("LK_ADMIN");
                break;
        }
    };

    private static ComponentRenderer<Span, User> createAuthComponentRenderer() {
        return new ComponentRenderer<>(Span::new, authComponentUpdater);
    }
}
