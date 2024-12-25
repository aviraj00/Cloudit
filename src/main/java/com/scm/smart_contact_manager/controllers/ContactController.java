package com.scm.smart_contact_manager.controllers;


import com.scm.smart_contact_manager.entities.Contact;
import com.scm.smart_contact_manager.entities.User;
import com.scm.smart_contact_manager.forms.ContactForm;
import com.scm.smart_contact_manager.helper.Helper;
import com.scm.smart_contact_manager.services.ContactService;
import com.scm.smart_contact_manager.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/user/contact")
public class ContactController {
    @Autowired
   private ContactService contactService;
    @Autowired
    private UserService userService;

    @RequestMapping("/add")
    public String addContactView(Model model){
        ContactForm contactForm=new ContactForm();
        contactForm.setName("aviraj");
        contactForm.setFavorite(true);
        model.addAttribute("contactForm",contactForm);
        return "user/add_contact";
    }
    @RequestMapping(value = "/add" ,method = RequestMethod.POST)
    public String saveContact(@ModelAttribute ContactForm contactForm, Authentication authentication, BindingResult bindingResult){

        if (bindingResult.hasErrors()){
            return "user/add_contact";
        }
        System.out.println(contactForm);
        Contact contact=new Contact();
String username= Helper.getEmailOfLoggedInUser(authentication);

User user =userService.getUserByEmail(username);
        contact.setName(contactForm.getName());
        contact.setAddress(contactForm.getAddress());
        contact.setEmail(contactForm.getEmail());
        contact.setFavorite(contactForm.getFavorite());
        contact.setDescription(contactForm.getDescription());
        contact.setPhonenumber(contactForm.getPhoneNumber());
        contact.setWebsiteLink(contactForm.getWebsiteLink());
        contact.setLinkedInLink(contactForm.getLinkedInLink());
        contact.setUser(user);


        contactService.save(contact);




            return "redirect:/user/contact/add";
    }


}
