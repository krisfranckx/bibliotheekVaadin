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
        Collection<SimpleGrantedAuthority> collection = new ArrayList<>();
        if (this.user.getAuth() != null && !this.user.getAuth().isEmpty()) {
            for (String auth : this.user.getAuth().split(",")) {
                String trimmedAuth = auth.trim();
                // Ensure role has ROLE_ prefix for Spring Security
                if (!trimmedAuth.startsWith("ROLE_")) {
                    trimmedAuth = "ROLE_" + trimmedAuth;
                }
                collection.add(new SimpleGrantedAuthority(trimmedAuth));
            }
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
        // Check if user is enabled
        return this.user.getEnabled() != null && this.user.getEnabled();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.user.getEnabled() != null && this.user.getEnabled();
    }

}