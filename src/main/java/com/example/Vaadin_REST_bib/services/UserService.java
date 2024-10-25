package com.example.Vaadin_REST_bib.services;

import com.example.Vaadin_REST_bib.config.Settings;
import com.example.Vaadin_REST_bib.restClasses.Book;
import com.example.Vaadin_REST_bib.restClasses.Response;
import com.example.Vaadin_REST_bib.restClasses.User;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class UserService implements HasUrlParameter<String> {

    private final WebClient webClient;
    private final WebClient webClient_register;

    public UserService(WebClient.Builder builder) {
        webClient = builder.baseUrl(Settings.BASE_URL_BACK+"/admin/").build();
        webClient_register = builder.baseUrl(Settings.BASE_URL_BACK+"/").build();
    }
    public Response getUsers(String value){

       return webClient.get().uri("/users?filter="+value).retrieve().bodyToMono(Response.class).block();
    }
    public Response getUser(Integer value){
        return webClient.get().uri(String.format("/users/%d",value)).retrieve().bodyToMono(Response.class).block();
    }
    public Response getUserByEmail(String email){
        return webClient.get().uri(String.format("/users/sec/%s",email)).retrieve().bodyToMono(Response.class).block();
    }
    @Override
    public void setParameter(BeforeEvent beforeEvent, String s) {

    }
    public Response saveUser(User user){
        return webClient.post().uri("/users").contentType(MediaType.APPLICATION_JSON).bodyValue(user).retrieve().bodyToMono(Response.class).block();
    }
    public Response deleteUser(User user){
        return webClient.delete().uri("/users/"+user.getId()).retrieve().bodyToMono(Response.class).block();
    }
    public int countUsers() {
        return getUsers("").getData().getUsers().size();
    }
    public Response register(User user){
        return webClient_register.post().uri("/api/registration").contentType(MediaType.APPLICATION_JSON).bodyValue(user).retrieve().bodyToMono(Response.class).block();
    }

}
