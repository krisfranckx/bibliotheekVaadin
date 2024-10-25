package com.example.Vaadin_REST_bib.config;

import com.example.Vaadin_REST_bib.restClasses.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class CustomSecurityUser extends User implements UserDetails {
    private User user;

    public CustomSecurityUser(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<SimpleGrantedAuthority> collection = new ArrayList();
        for (String auth:this.user.getAuth().split(",")) {
            collection.add(new SimpleGrantedAuthority(auth));
        }
        return collection;
    }

    @Override
    public String getPassword() {
        return this.user.getPaswoord();
    }

    @Override
    public String getUsername() {
        return this.user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.getEnabled();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}