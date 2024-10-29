package com.example.Vaadin_REST_bib.services;

import com.example.Vaadin_REST_bib.config.Settings;
import com.example.Vaadin_REST_bib.restClasses.Response;
import com.example.Vaadin_REST_bib.restClasses.User;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class ConfirmationService implements HasUrlParameter<String> {

    private final WebClient webClient;

    public ConfirmationService(WebClient.Builder builder) {
        webClient = builder.baseUrl(Settings.BASE_URL_BACK+"/api/registration/confirm").build();
    }
    public Response confirmWithUserclick(String value, String userclick){
        return webClient.get().uri("?token="+value+"&userclick="+userclick).retrieve().bodyToMono(Response.class).block();
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, String s) {

    }


}
