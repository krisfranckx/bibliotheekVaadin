package com.example.Vaadin_REST_bib.views;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.component.page.Meta;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

@PWA(name = "BibAtheneum", shortName = "BibAtheneum")
@Meta(name = "viewport", content = "width=device-width, initial-scale=1.0")
@StyleSheet(Lumo.UTILITY_STYLESHEET)
// Here, we add the style sheet to the global scope
@Theme("my-theme")
public class AppShellConfig implements AppShellConfigurator {

}
