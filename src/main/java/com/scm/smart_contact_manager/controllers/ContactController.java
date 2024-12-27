package com.scm.smart_contact_manager.controllers;


import com.scm.smart_contact_manager.entities.Contact;
import com.scm.smart_contact_manager.entities.User;
import com.scm.smart_contact_manager.forms.ContactForm;
import com.scm.smart_contact_manager.helper.Helper;
import com.scm.smart_contact_manager.helper.Message;
import com.scm.smart_contact_manager.helper.MessageType;
import com.scm.smart_contact_manager.services.ContactService;
import com.scm.smart_contact_manager.services.UserService;
import com.scm.smart_contact_manager.services.implimentation.ImageServiceImp;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.UUID;

@Controller
@RequestMapping("/user/contact")
public class ContactController {
    @Autowired
    public ImageServiceImp imageServiceImp;

    private final Logger logger = LoggerFactory.getLogger(ContactController.class);

    private final ContactService contactService;
    private final UserService userService;


    public ContactController(ContactService contactService, UserService userService) {
        this.contactService = contactService;
        this.userService = userService;
    }

    @RequestMapping("/add")
    public String addContactView(Model model) {
        ContactForm contactForm = new ContactForm();


        model.addAttribute("contactForm", contactForm);
        return "user/add_contact";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String saveContact(@Valid @ModelAttribute ContactForm contactForm, BindingResult result, Authentication authentication, HttpSession session) {

        if (result.hasErrors()) {
            session.setAttribute("message", Message.builder().content("Please Correct The Following").type(MessageType.red).build());
            return "user/add_contact";
        }
        String filename = UUID.randomUUID().toString();
/*
        logger.info("File info :{}", contactForm.getContactImage().getOriginalFilename());
*/
        String fileUrl = imageServiceImp.uploadImage(contactForm.getContactImage(), filename);

        String username = Helper.getEmailOfLoggedInUser(authentication);
        User user = userService.getUserByEmail(username);
        Contact contact = new Contact();
        contact.setName(contactForm.getName());
        contact.setAddress(contactForm.getAddress());
        contact.setEmail(contactForm.getEmail());
        contact.setFavorite(contactForm.getFavorite());
        contact.setDescription(contactForm.getDescription());
        contact.setPhonenumber(contactForm.getPhoneNumber());
        contact.setWebsiteLink(contactForm.getWebsiteLink());
        contact.setLinkedInLink(contactForm.getLinkedInLink());
        contact.setPicture(fileUrl);
        contact.setCloudinaryImagePublicId(filename);
        contact.setUser(user);


        contactService.save(contact);


        session.setAttribute("message", Message.builder().content("Your Contact Has Been Added Successfully!").type(MessageType.green).build());
        return "redirect:/user/contact/add";
    }


}
