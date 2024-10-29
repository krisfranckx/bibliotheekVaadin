
package com.example.Vaadin_REST_bib.restClasses;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.springframework.web.servlet.view.RedirectView;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "books", "magazines", "users"
})
public class Data {

    @JsonProperty("magazines")
    private List<Magazine> magazines;
    @JsonProperty("books")
    private List<Book> books;
    @JsonProperty("lkbooks")
    private List<LKBook> lkbooks;

    @JsonProperty("lkBooksAllWithFilter")
    private List<LKBook> lkBooksAllWithFilter;

    @JsonProperty("numberofBooksWithFilter")
    private int numberofBooksWithFilter;

    @JsonProperty("booksAllWithFilter")
    private List<Book> booksAllWithFilter;

    @JsonProperty("lkbooksStream")
    private Stream<LKBook> lkbooksStream;
    @JsonProperty("users")
    private List<User> users;
    @JsonProperty("user")
    private User user;
    @JsonProperty("securityUser")
    private Optional<User> securityUser;
    @JsonProperty("book")
    private Book book;
    @JsonProperty("lkbook")
    private LKBook lkbook;
    @JsonProperty("magazine")
    private Magazine magazine;
    @JsonProperty("view")
    private RedirectView view;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    @JsonProperty("books")
    public List<Book> getBooks() {
        return books;
    }

    @JsonProperty("books")
    public void setBooks(List<Book> books) {
        this.books = books;
    }

    @JsonProperty("lkbooks")
    public List<LKBook> getLKBooks() {
        return lkbooks;
    }

    @JsonProperty("lkbooks")
    public void setLKBooks(List<LKBook> books) {
        this.lkbooks = books;
    }

    @JsonProperty("numberofBooksWithFilter")
    public int getnumberofBooksWithFilter() {
        return numberofBooksWithFilter;
    }

    @JsonProperty("numberofBooksWithFilter")
    public void setnumberofBooksWithFilter(int numberofBooksWithFilter) {
        this.numberofBooksWithFilter = numberofBooksWithFilter;
    }

    @JsonProperty("lkBooksAllWithFilter")
    public List<LKBook> getLKBooksAllWithFilter() {
        return lkBooksAllWithFilter;
    }

    @JsonProperty("lkBooksAllWithFilter")
    public void setLKBooksAllWithFilter(List<LKBook> lkBooksAllWithFilter) {
        this.lkBooksAllWithFilter = lkBooksAllWithFilter;
    }

    @JsonProperty("booksAllWithFilter")
    public List<Book> getBooksAllWithFilter() {
        return booksAllWithFilter;
    }

    @JsonProperty("BooksAllWithFilter")
    public void setBooksAllWithFilter(List<Book> BooksAllWithFilter) {
        this.booksAllWithFilter = BooksAllWithFilter;
    }


    @JsonProperty("lkbooksStream")
    public Stream<LKBook> getLKBooksStream() {
        return lkbooksStream;
    }

    @JsonProperty("lkbooksStream")
    public void setLKBooksStream(Stream<LKBook> lkbooksStream) {
        this.lkbooksStream = lkbooksStream;
    }


    @JsonProperty("magazines")
    public List<Magazine> getMagazines() {
        return magazines;
    }

    @JsonProperty("magazines")
    public void setMagazines(List<Magazine> magazines) {
        this.magazines = magazines;
    }


    @JsonProperty("view")
    public RedirectView getView() {
        return view;
    }

    @JsonProperty("view")
    public void setView(RedirectView magazines) {
        this.view = view;
    }

    @JsonProperty("users")
    public List<User> getUsers() {
        return users;
    }

    @JsonProperty("users")
    public void setUsers(List<User> users) {
        this.users = users;
    }


    @JsonProperty("user")
    public User getUser() {
        return user;
    }

    @JsonProperty("user")
    public void setUser(User user) {
        this.user = user;
    }

    @JsonProperty("securityUser")
    public Optional<User> getsecurityUser() {
        return securityUser;
    }

    @JsonProperty("securityUser")
    public void setSecurityUser(Optional<User> securityUser) {
        this.securityUser = securityUser;
    }

    @JsonProperty("book")
    public Book getBook() {
        return book;
    }

    @JsonProperty("book")
    public void setBook(Book book) {
        this.book = book;
    }

    @JsonProperty("lkbook")
    public LKBook getLKBook() {
        return lkbook;
    }

    @JsonProperty("lkbook")
    public void setLKBook(LKBook book) {
        this.lkbook = book;
    }

    @JsonProperty("magazine")
    public Magazine getMagazine() {
        return magazine;
    }

    @JsonProperty("magazine")
    public void setMagazine(Magazine magazine) {
        this.magazine = magazine;
    }
    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
