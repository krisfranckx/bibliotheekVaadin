package com.example.Vaadin_REST_bib.config;

import com.example.Vaadin_REST_bib.views.login.LoginView;
import com.vaadin.flow.spring.security.VaadinSecurityConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.reactive.function.client.WebClient;


@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private final UserDetailsService userDetailsService;

    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        // Hide user not found exceptions to prevent username enumeration
        authProvider.setHideUserNotFoundExceptions(false);
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authManagerBuilder.authenticationProvider(authenticationProvider());
        return authManagerBuilder.build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        String adminBooksPath = Settings.BASE_URL_BACK + "/admin/books";
        
        http
            // CSRF configuration
            .csrf(csrf -> csrf
                .ignoringRequestMatchers(request ->
                    request.getRequestURI().startsWith(adminBooksPath)
                )
            )
            
            // Authorization configuration
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/login", "/login/**", "/registration", "/error").permitAll()
                .requestMatchers("/VAADIN/**", "/PUSH_RESOURCE/**", "/HEARTBEAT").permitAll()
                .anyRequest().authenticated()
            )
            
            // Apply Vaadin security - this handles form login integration with Vaadin's LoginForm
            .with(VaadinSecurityConfigurer.vaadin(), configurer -> {
                configurer.loginView(LoginView.class);
            })
            
            // Configure logout
            .logout(logout -> logout
                .logoutSuccessUrl("/login")
                .permitAll()
            );
        
        return http.build();
    }

}
