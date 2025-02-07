package com.example.Vaadin_REST_bib;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.server.AppShellSettings;
import com.vaadin.flow.server.PWA;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VaadinRestBibApplication{

	public static void main(String[] args) {
		SpringApplication.run(VaadinRestBibApplication.class, args);
	}

}
