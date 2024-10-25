package com.example.Vaadin_REST_bib.restClasses;

public enum Categorie_LK {
    Algemeen("Algemeen"),
    Didactiek("Didactiek"),
    Digitale_vorming("Digitale_vorming"),
    Fictie("Fictie");

    private final String categorie;

    Categorie_LK(String categorie) {
        this.categorie = categorie;
    }

    public String getCategorie() {
        return categorie;
    }
}
