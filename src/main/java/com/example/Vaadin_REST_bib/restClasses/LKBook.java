
package com.example.Vaadin_REST_bib.restClasses;

import com.fasterxml.jackson.annotation.*;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "type",
    "titel",
    "code",
    "uitgeleend",
    "geleendDoor",
    "uitgeleendTot",
    "gereserveerdDoor",
    "gereserveerdTot",
    "boekId",
    "auteur",
    "taal",
    "graad",
    "fictieNonFictie",
    "itemId"
})
public class LKBook extends BibItem{

    @JsonProperty("type")
    private String type;
    @JsonProperty("titel")
    private String titel;
    @JsonProperty("code")
    private String code;
    @JsonProperty("uitgeleend")
    private boolean uitgeleend;
    @JsonProperty("geleendDoor")
    private User geleendDoor;
    @JsonProperty("uitgeleendTot")
    private LocalDate uitgeleendTot;
    @JsonProperty("gereserveerdDoor")
    private User gereserveerdDoor;
    @JsonProperty("gereserveerdTot")
    private LocalDate gereserveerdTot;
    @JsonProperty("boekId")
    private Integer boekId;
    @JsonProperty("id_lener")
    private Integer id_lener;
    @JsonProperty("auteur")
    private String auteur;
    @JsonProperty("taal")
    private String taal;
    @JsonProperty("graad")
    private String graad;
    @JsonProperty("fictieNonFictie")
    private String fictieNonFictie;
    @JsonProperty("itemId")
    private Object itemId;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty("titel")
    public String getTitel() {
        return titel;
    }

    @JsonProperty("titel")
    public void setTitel(String titel) {
        this.titel = titel;
    }

    @JsonProperty("code")
    public String getCode() {
        return code;
    }

    @JsonProperty("code")
    public void setCode(String code) {
        this.code = code;
    }

    @JsonProperty("uitgeleend")
    public boolean getUitgeleend() {
        return uitgeleend;
    }

    @JsonProperty("uitgeleend")
    public void setUitgeleend(boolean uitgeleend) {
        this.uitgeleend = uitgeleend;
    }

    @JsonProperty("geleendDoor")
    public User getGeleendDoor() {
        return geleendDoor;
    }

    @JsonProperty("geleendDoor")
    public void setGeleendDoor(User geleendDoor) {
        this.geleendDoor = geleendDoor;
    }

    @JsonProperty("uitgeleendTot")
    public LocalDate getUitgeleendTot() {
        return uitgeleendTot;
    }

    @JsonProperty("uitgeleendTot")
    public void setUitgeleendTot(LocalDate uitgeleendTot) {
        this.uitgeleendTot = uitgeleendTot;
    }

    @JsonProperty("gereserveerdDoor")
    public User getGereserveerdDoor() {
        return gereserveerdDoor;
    }

    @JsonProperty("gereserveerdDoor")
    public void setGereserveerdDoor(User gereserveerdDoor) {
        this.gereserveerdDoor = gereserveerdDoor;
    }

    @JsonProperty("gereserveerdTot")
    public LocalDate getGereserveerdTot() {
        return gereserveerdTot;
    }

    @JsonProperty("gereserveerdTot")
    public void setGereserveerdTot(LocalDate gereserveerdTot) {
        this.gereserveerdTot = gereserveerdTot;
    }

    @JsonProperty("boekId")
    public Integer getBoekId() {
        return boekId;
    }

    @JsonProperty("boekId")
    public void setBoekId(Integer boekId) {
        this.boekId = boekId;
    }

    @JsonProperty("id_lener")
    public Integer getIdLener() {
        return id_lener;
    }

    @JsonProperty("id_lener")
    public void setIdLener(Integer id_lener) {
        this.id_lener = id_lener;
    }

    @JsonProperty("auteur")
    public String getAuteur() {
        return auteur;
    }

    @JsonProperty("auteur")
    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    @JsonProperty("taal")
    public String getTaal() {
        return taal;
    }

    @JsonProperty("taal")
    public void setTaal(String taal) {
        this.taal = taal;
    }

    @JsonProperty("graad")
    public String getGraad() {
        return graad;
    }

    @JsonProperty("graad")
    public void setGraad(String graad) {
        this.graad = graad;
    }

    @JsonProperty("fictieNonFictie")
    public String getFictieNonFictie() {
        return fictieNonFictie;
    }

    @JsonProperty("fictieNonFictie")
    public void setFictieNonFictie(String fictieNonFictie) {
        this.fictieNonFictie = fictieNonFictie;
    }

    @JsonProperty("itemId")
    public Object getItemId() {
        return itemId;
    }

    @JsonProperty("itemId")
    public void setItemId(Object itemId) {
        this.itemId = itemId;
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
