
package com.example.Vaadin_REST_bib.restClasses;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "voornaam",
    "familienaam",
    "email",
    "aantalItemsGeleend",
    "enabled",
    "auth",
    "enabledUI",
        "function"
})
public class User {

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("voornaam")
    private String voornaam;
    @JsonProperty("familienaam")
    private String familienaam;
    @JsonProperty("email")
    private String email;
    @JsonProperty("aantalItemsGeleend")
    private Integer aantalItemsGeleend;
    @JsonProperty("enabled")
    private Boolean enabled;
    @JsonProperty("auth")
    private String auth;
    @JsonProperty("paswoord")
    private String paswoord;
    @JsonProperty("enabledUI")
    private Boolean enabledUI;
    @JsonProperty("function")
    private String function;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    @JsonProperty("voornaam")
    public String getVoornaam() {
        return voornaam;
    }

    @JsonProperty("voornaam")
    public void setVoornaam(String voornaam) {
        this.voornaam = voornaam;
    }

    @JsonProperty("familienaam")
    public String getFamilienaam() {
        return familienaam;
    }

    @JsonProperty("familienaam")
    public void setFamilienaam(String familienaam) {
        this.familienaam = familienaam;
    }

    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    @JsonProperty("email")
    public void setEmail(String email) {
        this.email = email;
    }

    @JsonProperty("aantalItemsGeleend")
    public Integer getAantalItemsGeleend() {
        return aantalItemsGeleend;
    }

    @JsonProperty("aantalItemsGeleend")
    public void setAantalItemsGeleend(Integer aantalItemsGeleend) {
        this.aantalItemsGeleend = aantalItemsGeleend;
    }

    @JsonProperty("enabled")
    public Boolean getEnabled() {
        return enabled;
    }

    @JsonProperty("enabled")
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    @JsonProperty("auth")
    public String getAuth() {
        return auth;
    }

    @JsonProperty("auth")
    public void setAuth(String auth) {
        this.auth = auth;
    }

    @JsonProperty("paswoord")
    public String getPaswoord() {
        return paswoord;
    }

    @JsonProperty("paswoord")
    public void setPaswoord(String paswoord) {
        this.paswoord = paswoord;
    }

    @JsonProperty("enabledUI")
    public Boolean getEnabledUI() {
        return enabledUI;
    }

    @JsonProperty("enabledUI")
    public void setEnabledUI(Boolean enabledUI) {
        this.enabledUI = enabledUI;
    }

    @JsonProperty("function")
    public String getFunction() {
        return function;
    }

    @JsonProperty("function")
    public void setFunction(String function) {
        this.function = function;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        return "User{" +
                "voornaam='" + voornaam + '\'' +
                ", familienaam='" + familienaam + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return getEmail().equals(user.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEmail());
    }
}
