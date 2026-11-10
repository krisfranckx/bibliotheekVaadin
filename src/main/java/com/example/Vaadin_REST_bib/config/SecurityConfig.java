package com.example.Vaadin_REST_bib.config;

import com.example.Vaadin_REST_bib.views.login.LoginView;
import com.vaadin.flow.spring.security.VaadinWebSecurity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@EnableWebSecurity
@Configuration
public class SecurityConfig extends VaadinWebSecurity {

    private final CustomAuthSuccesHandler customAuthSuccesHandler;

    public SecurityConfig(CustomAuthSuccesHandler customAuthSuccesHandler) {
        this.customAuthSuccesHandler = customAuthSuccesHandler;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.ignoringRequestMatchers(antMatchers(Settings.BASE_URL_BACK+"/admin/books")));
        super.configure(http);
        setLoginView(http, LoginView.class);
        http.formLogin(form -> form.successHandler(customAuthSuccesHandler));
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
