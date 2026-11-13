package com.example.Vaadin_REST_bib.config;

import com.example.Vaadin_REST_bib.views.login.LoginView;
import com.vaadin.flow.spring.security.VaadinSecurityConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.reactive.function.client.WebClient;


@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private final CustomAuthSuccesHandler customAuthSuccesHandler;

    public SecurityConfig(CustomAuthSuccesHandler customAuthSuccesHandler) {
        this.customAuthSuccesHandler = customAuthSuccesHandler;
    }

    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        String adminBooksPath = Settings.BASE_URL_BACK+"/admin/books";
        http.csrf(csrf -> csrf.ignoringRequestMatchers(request ->
            request.getRequestURI().startsWith(adminBooksPath)
        ));
        
        return http
            .with(VaadinSecurityConfigurer.vaadin(), configurer -> {
                configurer.loginView(LoginView.class);
            })
            .formLogin(form -> form.successHandler(customAuthSuccesHandler))
            .build();
    }

  /*  @Bean
    public UserDetailsManager userDetailsService() {
        UserDetails user =
                User.withUsername("user")
                        .password("{noop}user")
                        .roles("USER")
                        .build();
        UserDetails admin =
                User.withUsername("admin")
                        .password("{noop}admin")
                        .roles("ADMIN")
                        .build();
        return new InMemoryUserDetailsManager(user, admin);
    }*/

    @Bean
    BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
