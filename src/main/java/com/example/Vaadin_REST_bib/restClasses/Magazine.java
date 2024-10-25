
package com.example.Vaadin_REST_bib.restClasses;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

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
    "magazineId",
    "nummertijdschrift",
    "jaargang",
    "itemId"
})
public class Magazine extends BibItem{

    @JsonProperty("type")
    private String type;
    @JsonProperty("titel")
    private String titel;
    @JsonProperty("naamtijdschrift")
    private String naamtijdschrift;
    @JsonProperty("code")
    private String code;
    @JsonProperty("code_tijdschrift")
    private String code_tijdschrift;
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
    @JsonProperty("magazineId")
    private Integer magazineId;
    @JsonProperty("tijdschriftid")
    private Integer tijdschriftid;
    @JsonProperty("nummertijdschrift")
    private Integer nummertijdschrift;
    @JsonProperty("jaargang")
    private Integer jaargang;
    @JsonProperty("itemId")
    private Long itemId;
    @JsonProperty("id_lener")
    private Integer id_lener;
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

    @JsonProperty("naamtijdschrift")
    public String getNaamtijdschrift() {
        return naamtijdschrift;
    }

    @JsonProperty("naamtijdschrift")
    public void setNaamtijdschrift(String naamtijdschrift) {
        this.naamtijdschrift = naamtijdschrift;
    }

    @JsonProperty("code")
    public String getCode() {
        return code;
    }

    @JsonProperty("code")
    public void setCode(String code) {
        this.code = code;
    }

    @JsonProperty("code_tijdschrift")
    public String getCode_tijdschrift() {
        return code_tijdschrift;
    }

    @JsonProperty("code_tijdschrift")
    public void setCode_tijdschrift(String code_tijdschrift) {
        this.code_tijdschrift = code_tijdschrift;
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

    @JsonProperty("magazineId")
    public Integer getMagazineId() {
        return magazineId;
    }

    @JsonProperty("magazineId")
    public void setMagazineId(Integer magazineId) {
        this.magazineId = magazineId;
    }

    @JsonProperty("tijdschriftid")
    public Integer getTijdschriftid() {
        return tijdschriftid;
    }

    @JsonProperty("tijdschriftid")
    public void setTijdschriftid(Integer tijdschriftid) {
        this.tijdschriftid = tijdschriftid;
    }

    @JsonProperty("nummertijdschrift")
    public Integer getNummertijdschrift() {
        return nummertijdschrift;
    }

    @JsonProperty("nummertijdschrift")
    public void setNummertijdschrift(Integer nummertijdschrift) {
        this.nummertijdschrift = nummertijdschrift;
    }

    @JsonProperty("jaargang")
    public Integer getJaargang() {
        return jaargang;
    }

    @JsonProperty("jaargang")
    public void setJaargang(Integer jaargang) {
        this.jaargang = jaargang;
    }

    @JsonProperty("itemId")
    public Long getItemId() {
        return itemId;
    }

    @JsonProperty("itemId")
    public void setItemId(Long itemId) {
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

    @JsonProperty("id_lener")
    public Integer getIdLener() {
        return id_lener;
    }

    @JsonProperty("id_lener")
    public void setIdLener(Integer id_lener) {
        this.id_lener = id_lener;
    }
}
