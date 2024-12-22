package com.scm.smart_contact_manager.controllers;

import com.scm.smart_contact_manager.entities.User;
import com.scm.smart_contact_manager.helper.Helper;
import com.scm.smart_contact_manager.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class RootController {

    @Autowired
    private UserService userService;
    private Logger logger= LoggerFactory.getLogger(this.getClass());

    @ModelAttribute
    public void addLoggedInUserInfo(Model model, Authentication authentication){
        if (authentication == null) {
            return;
        }
        System.out.println("Adding Logged in User Information to the model");
        String username=  Helper.getEmailOfLoggedInUser(authentication);

        User user= userService.getUserByEmail(username);
        System.out.println(user);
        System.out.println(user.getName());
        System.out.println(user.getEmail());
        model.addAttribute("loggedInUser",user);
    }

}
