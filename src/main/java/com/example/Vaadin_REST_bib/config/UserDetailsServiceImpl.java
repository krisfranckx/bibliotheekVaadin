package com.example.Vaadin_REST_bib.config;


import com.example.Vaadin_REST_bib.restClasses.User;
import com.example.Vaadin_REST_bib.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
    UserService userService;

    public UserDetailsServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        logger.debug("Loading user details for email: {}", email);
        try {
            Optional<User> userOpt = userService
                    .getUserByEmail(email)
                    .getData()
                    .getsecurityUser();
            
            if (userOpt.isEmpty()) {
                logger.warn("User not found with email: {}", email);
                throw new UsernameNotFoundException("Username not found: " + email);
            }
            
            User user = userOpt.get();
            logger.debug("User found: {}, enabled: {}, auth: {}", user.getEmail(), user.getEnabled(), user.getAuth());
            return new CustomSecurityUser(user);
        } catch (Exception ex) {
            logger.error("Error loading user details for email: {}", email, ex);
            throw new UsernameNotFoundException("Username not found: " + email, ex);
        }
    }

}
