package com.scm.smart_contact_manager.controllers;


import com.scm.smart_contact_manager.entities.User;
import com.scm.smart_contact_manager.forms.UserForm;
import com.scm.smart_contact_manager.helper.Message;
import com.scm.smart_contact_manager.helper.MessageType;
import com.scm.smart_contact_manager.services.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.naming.Binding;

@Controller
public class PageController {

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @GetMapping("/")
    public String main(){
        return "redirect:/home";
    }

    @RequestMapping("/home")
    public String home() {
        System.out.println("Home Page Handler");
        return "home";
    }

    @RequestMapping("/about")
    public String aboutPage() {
        System.out.println("About Page Loading");
        return "about";
    }

    @RequestMapping("/services")
    public String services() {
        System.out.println("Services chalri hai");
        return "services";
    }

    @GetMapping("/contacts")
    public String contacts() {
        System.out.println("contact ");
        return "contact";
    }

    @GetMapping("/login")
    public String login() {
        return new String("login");
    }

    @GetMapping("/signup")
    public String signup(Model model) {
        UserForm userForm=new UserForm();
       model.addAttribute("userForm", userForm);
        return "signup";
    }
    //processing for register

    @RequestMapping(value = "/do-register", method = RequestMethod.POST)
    public String processRegister(@Valid @ModelAttribute UserForm userForm, BindingResult rBindingResult, HttpSession session) {
        System.out.println("processing");
        System.out.println(userForm);
       // validate form data

        if(rBindingResult.hasErrors()){
            return "signup";
        }

      /* User user= User.builder()
               .name(userForm.getName())
               .email(userForm.getEmail())
               .password(userForm.getPassword()) // Hash password in real scenarios
               .about(userForm.getAbout())
               .phonenumber(userForm.getPhonenumber())
               .profilPic("static/images/profile.png")
              // Empty contact list initially
               .build();*/
    User user= new User();
    user.setName(userForm.getName());
        user.setEmail(userForm.getEmail());
        user.setPassword(userForm.getPassword());        /*user. setAbout (userForm.getAbout());*/
        user.setPhoneNumber(userForm.getPhoneNumber());
        user.setEnabled(true);
        user.setProfilPic("static/images/profile.png");
        user.setAbout(userForm.getAbout());


        User savedUser= userService.saveUser(user);
        System.out.println("user saved");

        //FETCH FORM DATA
        //user form class
        //VALIDATE FORM DATA
        //SAVE TO DATABASE
        //MESSAGE = "REGISTRATION SUCCESSFUL
        //redirect login page
        // add message
       Message message= Message.builder().content("Registration Successful").type(MessageType.green).build();
         session.setAttribute("message",message);
        return "redirect:/signup";
    }
}
