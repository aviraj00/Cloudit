package com.scm.smart_contact_manager.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.scm.smart_contact_manager.services.implimentation.SecurityCustomUserDetails;

@Configuration
public class SecurityConfig {

    @Autowired
    private OAuthenticationSuccessHandler oAuthenticationSuccessHandler;

    @Autowired
    private AuthFailureHandler authFailureHandler;

    @Autowired
    private SecurityCustomUserDetails userDetailsService;

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests(authorize -> {
                    authorize
                            .requestMatchers("/home", "/signup", "/login", "/authenticate", "/resources/**", "/static/**").permitAll()
                            .requestMatchers("/user/**").authenticated()
                            .anyRequest().permitAll();
                })
                .formLogin(formLogin -> {
                    formLogin
                            .loginPage("/login") // Custom login page
                            .loginProcessingUrl("/authenticate") // URL for login form submission
                            // Redirect on authentication failure
                            .defaultSuccessUrl("/user/profile", true) // Redirect on successful login
                            .usernameParameter("email") // Custom parameter for username
                            .passwordParameter("password")
                            .failureHandler(authFailureHandler);
                })
                .csrf(AbstractHttpConfigurer::disable) // Disable CSRF (optional, depends on your needs)
                .logout(logout -> {
                    logout
                            .logoutUrl("/do-logout") // Custom logout URL
                            .logoutSuccessUrl("/login?logout=true"); // Redirect after logout
                })
                .oauth2Login(oauth -> {
                    oauth
                            .loginPage("/login") // OAuth2 login page
                            .successHandler(oAuthenticationSuccessHandler); // Custom success handler
                });

        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}