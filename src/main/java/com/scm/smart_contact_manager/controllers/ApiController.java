package com.scm.smart_contact_manager.controllers;


import com.scm.smart_contact_manager.entities.Contact;
import com.scm.smart_contact_manager.entities.User;
import com.scm.smart_contact_manager.services.ContactService;
import com.scm.smart_contact_manager.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiController {
   @Autowired
    private ContactService contactService;

    @Autowired
    private UserService userService;

   @GetMapping("/contact/{contactId}")
    public Contact getContact(@PathVariable String contactId){
       return contactService.getById(contactId);
    }

    @GetMapping("/user")
    public List<User> user(){
       return userService.getAllUser();
    }
}
