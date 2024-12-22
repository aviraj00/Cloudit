package com.scm.smart_contact_manager.controllers;


import com.scm.smart_contact_manager.entities.User;
import com.scm.smart_contact_manager.helper.Helper;
import com.scm.smart_contact_manager.services.UserService;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/user")
public class UserController {



    private Logger logger= LoggerFactory
            .getLogger(UserController.class);
    @Autowired
    private UserService userService;

    @ModelAttribute
    public void addLoggedInUserInfo(Model model,Authentication authentication){
        String username=  Helper.getEmailOfLoggedInUser(authentication);
        User user= userService.getUserByEmail(username);

        logger.info("User Login in:{}",username);
        System.out.println(user.getName());
        System.out.println(user.getEmail());
        model.addAttribute("loggedInUser",user);
    }

    @RequestMapping("/dashboard")
    private String userDashboard(){
        System.out.println("User dashboard");
        return "user/dashboard";
    }

    @RequestMapping("/profile")
    private String userProfile(Model model,Authentication authentication){
      String username=  Helper.getEmailOfLoggedInUser(authentication);
       User user= userService.getUserByEmail(username);

        logger.info("User Login in:{}",username);
        System.out.println(user.getName());
        System.out.println(user.getEmail());
        model.addAttribute("loggedInUser",user);
        return "user/profile";
    }
}
