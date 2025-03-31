package com.scm.smart_contact_manager.config;


import com.scm.smart_contact_manager.entities.Provider;
import com.scm.smart_contact_manager.entities.User;
import com.scm.smart_contact_manager.helper.AppConstants;
import com.scm.smart_contact_manager.repositories.UserRepo;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.UUID;


@Component
public class OAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    private UserRepo userRepo;

    Logger logger= LoggerFactory.getLogger(OAuthenticationSuccessHandler.class);
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
logger.info("OAuthenticationSuccessHandler");

//google
        var oauth2AuthenticationToken =(OAuth2AuthenticationToken)authentication;

     String authorizedClientRegistrationId=oauth2AuthenticationToken.getAuthorizedClientRegistrationId();

     logger.info(authorizedClientRegistrationId);

        var oauth2User=(DefaultOAuth2User)authentication.getPrincipal();

        oauth2User.getAttributes().forEach((key,value)->{
            logger.info(key+" : "+value);
        });

        User user=new User();
        user.setUserId(UUID.randomUUID().toString());
        user.setRoleList(List.of(AppConstants.ROLE_USER));
        user.setEmailVerified(true);
        user.setEnabled(true);
        //google

     if (authorizedClientRegistrationId.equalsIgnoreCase("google")){
        user.setEmail(oauth2User.getAttribute("email").toString());
        user.setProfilPic(oauth2User.getAttribute("picture").toString());
       user.setName(oauth2User.getAttribute("name").toString());
       user.setProvider(Provider.GOOGLE);
       user.setProviderId(oauth2User.getName());
         user.setAbout("Created using Google");


     }
     else if (authorizedClientRegistrationId.equalsIgnoreCase("github"))
     {
         //github
         String email=oauth2User.getAttribute("email") !=null?oauth2User.getAttribute("email").toString():oauth2User.getAttribute("login").toString()+"@gmail.com";
         String picture=oauth2User.getAttribute("avatar_url").toString();
         String name=oauth2User.getAttribute("login").toString();
         String providerid=oauth2User.getName();

         user.setEmail(email);
         user.setName(name);
         user.setProfilPic(picture);
         user.setProviderId(providerid);
         user.setProvider(Provider.GITHUB);
         user.setEmailVerified(true);
         user.setEnabled(true);
         user.setAbout("Created using Github");






     }     else{

     }




/*
response.sendRedirect("/home");
*/
        //data databasesave
        /*
        DefaultOAuth2User user=(DefaultOAuth2User)authentication.getPrincipal();

        /*logger.info(user.getName());
        user.getAttributes().forEach((key,value)->{
            logger.info("{}=>{}",key,value);
        });
        logger.info(user.getAuthorities().toString());

        String email=user.getAttribute("email");
        String name=user.getAttribute("name").toString();
        String picture=user.getAttribute("picture");

//create user and save to database
        User user1= new User();
        user1.setEmail(email);
        user1.setName(name);
        user1.setProfilPic(picture);
        user1.setPassword("default");
        user1.setUserId(UUID.randomUUID().toString());
        user1.setProvider(Provider.GOOGLE);
        user1.setEnabled(true);
        user1.setEmailVerified(true);
        user1.setProviderId(user.getName());
        user1.setRoleList(List.of(AppConstants.ROLE_USER));
        user1.setAbout("Created using Google");

      User user2=  userRepo.findByEmail(email).orElse(null);
      if (user2==null){
          userRepo.save(user1);
          logger.info("user saved"+email);
      } */
        User user2=  userRepo.findByEmail(user.getEmail()).orElse(null);
        if (user2==null) {
            userRepo.save(user);
        }
        new DefaultRedirectStrategy().sendRedirect(request,response,"/user/profile");
    }
}
