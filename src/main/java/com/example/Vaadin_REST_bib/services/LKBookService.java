package com.example.Vaadin_REST_bib.services;

import com.example.Vaadin_REST_bib.config.Settings;
import com.example.Vaadin_REST_bib.restClasses.Book;
import com.example.Vaadin_REST_bib.restClasses.LKBook;
import com.example.Vaadin_REST_bib.restClasses.Response;
import com.example.Vaadin_REST_bib.restClasses.User;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.data.provider.SortOrder;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import static com.vaadin.flow.spring.data.VaadinSpringDataHelpers.toSpringDataSort;
import org.springframework.data.domain.Sort;


@Service
public class LKBookService implements HasUrlParameter<String> {

    private final WebClient webClient;

    public LKBookService(WebClient.Builder builder) {
        webClient = builder.baseUrl(Settings.BASE_URL_BACK+"/lkadmin/").build();
    }

    public Response getBooks(String value){
       return webClient.get().uri("/books?filter="+value).retrieve().bodyToMono(Response.class).block();
    }
    public Response getBook(Integer boekId) {
        return webClient.get().uri("/books/"+boekId).retrieve().bodyToMono(Response.class).block();
    }
    @Override
    public void setParameter(BeforeEvent beforeEvent, String s) {
    }
    public Response saveBook(LKBook book){
        return webClient.post().uri("/books").contentType(MediaType.APPLICATION_JSON).bodyValue(book).retrieve().bodyToMono(Response.class).block();
    }
    public Response updateBook(LKBook book){
        return webClient.put().uri(String.format("/books/%d",book.getBoekId())).contentType(MediaType.APPLICATION_JSON).bodyValue(book).retrieve().bodyToMono(Response.class).block();
    }
    public Response deleteBook(LKBook book){
        return webClient.delete().uri("/books/"+book.getBoekId()).retrieve().bodyToMono(Response.class).block();
    }
    public int countBooks() {
        return webClient.get().uri("/books/count").retrieve().bodyToMono(Integer.class).block();
    }
    public Response borrowBook(LKBook book, User user) {
        return webClient.get().uri(String.format("/books/%d/lenen/%d",book.getBoekId(),user.getId())).retrieve().bodyToMono(Response.class).block();
    }
    public Response reserveBook(LKBook book, User user) {
        return webClient.get().uri(String.format("/books/%d/reserveren/%d",book.getBoekId(),user.getId())).retrieve().bodyToMono(Response.class).block();
    }
    public Response bringBackBook(LKBook book, User user) {
        return webClient.get().uri(String.format("/books/%d/binnenleveren/%d",book.getBoekId(),user.getId())).retrieve().bodyToMono(Response.class).block();
    }
    public Response bookvrijgeven(LKBook book) {
        return webClient.get().uri(String.format("/books/%d/vrijgeven",book.getBoekId())).retrieve().bodyToMono(Response.class).block();
    }

    public Response increaseBorrowBook(LKBook item) {
        return webClient.get().uri(String.format("/books/%d/verlengen",item.getBoekId())).retrieve().bodyToMono(Response.class).block();
    }

    public Flux<LKBook> findAllDataprovider(int pageSize, int pageNumber, String value, String sort, SortDirection direction) {
        return webClient.get().uri("/books/provider?pageSize="+pageSize+"&pageNumber="+pageNumber+"&filter="+value+"&sort="+sort+"&direction="+direction).exchangeToFlux(res->res.bodyToFlux(LKBook.class));
    }

}
