package com.example.Vaadin_REST_bib.services;

import com.example.Vaadin_REST_bib.config.Settings;
import com.example.Vaadin_REST_bib.restClasses.Book;
import com.example.Vaadin_REST_bib.restClasses.Magazine;
import com.example.Vaadin_REST_bib.restClasses.Response;
import com.example.Vaadin_REST_bib.restClasses.User;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class MagazineService implements HasUrlParameter<String> {

    private final WebClient webClient;

    public MagazineService(WebClient.Builder builder) {
        webClient = builder.baseUrl(Settings.BASE_URL_BACK+"/admin/").build();
    }
    public Response getMagazines(String value){
       return webClient.get().uri("/magazines?filter="+value).retrieve().bodyToMono(Response.class).block();
    }
    public Response getMagazine(Integer magazineId) {
        return webClient.get().uri("/magazines/"+magazineId).retrieve().bodyToMono(Response.class).block();
    }
    @Override
    public void setParameter(BeforeEvent beforeEvent, String s) {}
    public Response saveMagazine(Magazine magazine){
        return webClient.post().uri("/magazines").contentType(MediaType.APPLICATION_JSON).bodyValue(magazine).retrieve().bodyToMono(Response.class).block();
    }
    public Response updateMagazine(Magazine magazine){
        return webClient.put().uri(String.format("/magazines/%d",magazine.getTijdschriftid())).contentType(MediaType.APPLICATION_JSON).bodyValue(magazine).retrieve().bodyToMono(Response.class).block();
    }
    public Response deleteMagazine(Magazine magazine){
        return webClient.delete().uri("/magazines/"+magazine.getTijdschriftid()).retrieve().bodyToMono(Response.class).block();
    }
    public int countMagazines() {
        return getMagazines("").getData().getMagazines().size();
    }
    public Response borrowMagazine(Magazine magazine, User user) {
        return webClient.get().uri(String.format("/magazines/%d/lenen/%d",magazine.getTijdschriftid(),user.getId())).retrieve().bodyToMono(Response.class).block();
    }
    public Response reserveMagazine(Magazine magazine, User user) {
        return webClient.get().uri(String.format("/magazines/%d/reserveren/%d",magazine.getTijdschriftid(),user.getId())).retrieve().bodyToMono(Response.class).block();
    }
    public Response bringBackMagazine(Magazine magazine, User user) {
        return webClient.get().uri(String.format("/magazines/%d/binnenleveren/%d",magazine.getTijdschriftid(),user.getId())).retrieve().bodyToMono(Response.class).block();
    }
    public Response magazineVrijgeven(Magazine magazine) {
        return webClient.get().uri(String.format("/magazines/%d/vrijgeven",magazine.getTijdschriftid())).retrieve().bodyToMono(Response.class).block();
    }
    public Response increaseBorrowMagazine(Magazine item) {
        return webClient.get().uri(String.format("/magazines/%d/verlengen",item.getTijdschriftid())).retrieve().bodyToMono(Response.class).block();
    }
}
