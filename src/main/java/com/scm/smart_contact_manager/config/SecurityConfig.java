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
    private SecurityCustomUserDetails userDetailsService;

    //user create and login using java code with memory service

    /*@Bean
    public UserDetailsService userDetailsService(){

       UserDetails user1= User.withDefaultPasswordEncoder().username("admin123").password("admin123").build();

       UserDetails user2=User.withUsername("user123").password("user123").build();
        return new InMemoryUserDetailsManager(user1,user2);
    }*/
     @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider= new DaoAuthenticationProvider();
         daoAuthenticationProvider.setUserDetailsService(userDetailsService);
         daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());

        return daoAuthenticationProvider;
    }



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
         httpSecurity.authorizeHttpRequests(authorize->{
/*
             authorize.requestMatchers("/home","/signup","services").permitAll();
*/           authorize.requestMatchers("/home", "/signup", "/login", "/authenticate", "/resources/**", "/static/**").permitAll();
             authorize.requestMatchers("/user/**").authenticated();
             authorize.anyRequest().permitAll();
         });

        httpSecurity.formLogin(formLogin -> {

            //
            formLogin.loginPage("/login");
            formLogin.loginProcessingUrl("/authenticate");
            formLogin.failureForwardUrl("/login?error=true");
            formLogin.defaultSuccessUrl("/user/profile",true);
            // formLogin.failureForwardUrl("/login?error=true");
            // formLogin.defaultSuccessUrl("/home");
            formLogin.usernameParameter("email");
            formLogin.passwordParameter("password");
         });

        httpSecurity.csrf(AbstractHttpConfigurer::disable);

        httpSecurity.logout(logoutForm->{
           logoutForm.logoutUrl("/do-logout");
           logoutForm.logoutSuccessUrl("/login?logout=true");
        });
    //oauth config

        httpSecurity.oauth2Login(oauth->{
            oauth.loginPage("/login");
            oauth.successHandler(oAuthenticationSuccessHandler);
        });
         return httpSecurity.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
