package com.example.Vaadin_REST_bib.services;

import com.example.Vaadin_REST_bib.config.Settings;
import com.example.Vaadin_REST_bib.restClasses.*;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Service
public class BookService implements HasUrlParameter<String> {

    private final WebClient webClient;

    public BookService(WebClient.Builder builder) {
        webClient = builder.baseUrl(Settings.BASE_URL_BACK+"/admin/").build();
    }

    public Response getBooks(String value){
       return webClient.get().uri("/books?filter="+value).retrieve().bodyToMono(Response.class).block();
    }

    public Flux<Book> findAllDataprovider(int pageSize, int pageNumber, String value, String sort, SortDirection direction) {
        return webClient.get().uri("/books/provider?pageSize="+pageSize+"&pageNumber="+pageNumber+"&filter="+value+"&direction="+direction+"&sort="+sort).exchangeToFlux(res->res.bodyToFlux(Book.class));
    }

    public Response getBooksBorrowed(String value){
        return webClient.get().uri("/books/borrowed?borrowed=true&filter="+value).retrieve().bodyToMono(Response.class).block();
    }



    public Response getBook(Integer boekId) {
        return webClient.get().uri("/books/"+boekId).retrieve().bodyToMono(Response.class).block();
    }
    @Override
    public void setParameter(BeforeEvent beforeEvent, String s) {
    }
    public Response saveBook(Book book){
        return webClient.post().uri("/books").contentType(MediaType.APPLICATION_JSON).bodyValue(book).retrieve().bodyToMono(Response.class).block();
    }
    public Response updateBook(Book book){
        return webClient.put().uri(String.format("/books/%d",book.getBoekId())).contentType(MediaType.APPLICATION_JSON).bodyValue(book).retrieve().bodyToMono(Response.class).block();
    }
    public Response deleteBook(Book book){
        return webClient.delete().uri("/books/"+book.getBoekId()).retrieve().bodyToMono(Response.class).block();
    }
    public int countBooks() {
        //return getBooks("").getData().getnumberofBooksWithFilter();
        return webClient.get().uri("/books/count").retrieve().bodyToMono(Integer.class).block();
    }
    public Response borrowBook(Book book, User user) {
        return webClient.get().uri(String.format("/books/%d/lenen/%d",book.getBoekId(),user.getId())).retrieve().bodyToMono(Response.class).block();
    }
    public Response reserveBook(Book book, User user) {
        return webClient.get().uri(String.format("/books/%d/reserveren/%d",book.getBoekId(),user.getId())).retrieve().bodyToMono(Response.class).block();
    }
    public Response bringBackBook(Book book, User user) {
        return webClient.get().uri(String.format("/books/%d/binnenleveren/%d",book.getBoekId(),user.getId())).retrieve().bodyToMono(Response.class).block();
    }
    public Response bookvrijgeven(Book book) {
        return webClient.get().uri(String.format("/books/%d/vrijgeven",book.getBoekId())).retrieve().bodyToMono(Response.class).block();
    }

    public Response increaseBorrowBook(Book item) {
        return webClient.get().uri(String.format("/books/%d/verlengen",item.getBoekId())).retrieve().bodyToMono(Response.class).block();
    }
}
