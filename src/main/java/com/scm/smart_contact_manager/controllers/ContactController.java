package com.scm.smart_contact_manager.controllers;

import com.scm.smart_contact_manager.entities.Contact;
import com.scm.smart_contact_manager.entities.User;
import com.scm.smart_contact_manager.forms.ContactForm;
import com.scm.smart_contact_manager.forms.ContactSearchForm;
import com.scm.smart_contact_manager.helper.AppConstants;
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
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/user/contact")
public class ContactController {

    public final ImageServiceImp imageServiceImp;
    private final ContactService contactService;
    private final UserService userService;
    Logger logger = LoggerFactory.getLogger(ContactController.class);


    public ContactController(ContactService contactService, UserService userService, ImageServiceImp imageServiceImp) {
        this.contactService = contactService;
        this.userService = userService;
        this.imageServiceImp = imageServiceImp;
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
        /*String filename = UUID.randomUUID().toString();
*//*
        logger.info("File info :{}", contactForm.getContactImage().getOriginalFilename());
*//*
        String fileUrl = imageServiceImp.uploadImage(contactForm.getContactImage(), filename);*/

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

      if (contactForm.getContactImage()!=null && !contactForm.getContactImage().isEmpty()){
          String filename = UUID.randomUUID().toString();
/*
        logger.info("File info :{}", contactForm.getContactImage().getOriginalFilename());
*/
          String fileUrl = imageServiceImp.uploadImage(contactForm.getContactImage(), filename);

          contact.setPicture(fileUrl);
          contact.setCloudinaryImagePublicId(filename);
      }
        contact.setUser(user);


        contactService.save(contact);


        session.setAttribute("message", Message.builder().content("Your Contact Has Been Added Successfully!").type(MessageType.green).build());
        return "redirect:/user/contact/add";
    }

    @RequestMapping
    public String viewContacts(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "size", defaultValue = AppConstants.PAGE_SIZE + "") int size, @RequestParam(value = "sortBy", defaultValue = "name") String sortBy, @RequestParam(value = "direction", defaultValue = "asc") String direction, Model model, Authentication authentication) {
        String username = Helper.getEmailOfLoggedInUser(authentication);
        User user = userService.getUserByEmail(username);
        Page<Contact> pageContact = contactService.getByUser(user, page, size, sortBy, direction);
        model.addAttribute("pageContact", pageContact);
        model.addAttribute("pageSize", AppConstants.PAGE_SIZE);
        model.addAttribute("contactSearchForm", new ContactSearchForm());
        return "user/contacts";
    }

    //search handler
    @RequestMapping("/search")
    public String searchHandler(@ModelAttribute ContactSearchForm contactSearchForm, @RequestParam(value = "size", defaultValue = AppConstants.PAGE_SIZE + "") int size, @RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "sortBy", defaultValue = "name") String sortBy, @RequestParam(value = "direction", defaultValue = "asc") String direction, Model model, Authentication authentication) {

        var user = userService.getUserByEmail(Helper.getEmailOfLoggedInUser(authentication));
        Page<Contact> pageContact = null;
        if (contactSearchForm.getField().equalsIgnoreCase("name")) {
            pageContact = contactService.searchByName(contactSearchForm.getValue(), page, size, sortBy, direction, user);
        } else if (contactSearchForm.getField().equalsIgnoreCase("email")) {
            pageContact = contactService.searchByEmail(contactSearchForm.getValue(), page, size, sortBy, direction, user);
        } else if (contactSearchForm.getField().equalsIgnoreCase("phone")) {
            pageContact = contactService.searchByPhoneNumber(contactSearchForm.getValue(), page, size, sortBy, direction, user);
        }
        model.addAttribute("pageContact", pageContact);
        model.addAttribute("contactSearchForm", contactSearchForm);
        model.addAttribute("pageSize", AppConstants.PAGE_SIZE);

        return "user/search";


    }

    //delete
    @RequestMapping("/delete/{contactId}")
    public String deleteContact(@PathVariable String contactId, HttpSession session) {

        contactService.delete(contactId);
        logger.info("contactdelte:{}", contactId);
        session.setAttribute("message", Message.builder().content("Contact Delete Successfully").type(MessageType.green).build());
        return "redirect:/user/contact";
    }

    //upadte contact view
    @GetMapping("/view/{contactId}")
    public String updateContactFormView(@PathVariable String contactId, Model model) {
       var contact= contactService.getById(contactId);
       ContactForm contactForm=new ContactForm();
       contactForm.setName(contact.getName());
       contactForm.setEmail(contact.getEmail());
       contactForm.setPhoneNumber(contact.getPhonenumber());
        contactForm.setAddress(contact.getAddress());
        contactForm.setDescription(contact.getDescription());
        contactForm.setPicture(contact.getPicture());
        contactForm.setFavorite(contact.isFavorite());
        contactForm.setWebsiteLink(contact.getWebsiteLink());
        contactForm.setLinkedInLink(contact.getLinkedInLink());

        model.addAttribute("contactForm", contactForm);
        model.addAttribute("contactId",contactId);
        return "user/view";
    }

    /*@PostMapping("/update/{contactId}")
    public String updateContact(@PathVariable("contactId") String contactId,
                                @Valid @ModelAttribute ContactForm contactForm,
                                BindingResult bindingResult,
                                Model model,HttpSession session) {
        if(bindingResult.hasErrors()){
         return  "user/view";
        }
     //update
        var con=contactService.getById(contactId);
        con.setId(contactId);
        con.setName(contactForm.getName());
        con.setAddress(contactForm.getAddress());
        con.setEmail(contactForm.getEmail());
        con.setFavorite(contactForm.getFavorite());
        con.setDescription(contactForm.getDescription());
        con.setPhonenumber(contactForm.getPhoneNumber());
        con.setWebsiteLink(contactForm.getWebsiteLink());
        con.setLinkedInLink(contactForm.getLinkedInLink());

        //image process
      if(contactForm.getContactImage()!=null && !contactForm.getContactImage().isEmpty()) {
          String fileName=UUID.randomUUID().toString();
          String imageUrl=imageServiceImp.uploadImage(contactForm.getContactImage(),fileName);
          con.setCloudinaryImagePublicId(fileName);
          con.setPicture(imageUrl);
          contactForm.setPicture(imageUrl);
      }
        var updateCon=contactService.update(con);
      //  model.addAttribute("contactForm",contactForm);
*//*
        session.setAttribute("message",Message.builder().content("Contact Update").type(MessageType.green).build());
*//*             model.addAttribute("message", Message.builder().content("Contact Updated !!").type(MessageType.green).build());
        return "redirect:/user/contact/view/" + contactId;
    }*/
    @RequestMapping(value = "/update/{contactId}", method = RequestMethod.POST)
    public String updateContact(@PathVariable("contactId") String contactId,
                                @Valid @ModelAttribute ContactForm contactForm,
                                BindingResult bindingResult,
                                Model model,HttpSession session) {

        // update the contact
        if (bindingResult.hasErrors()) {
            return "user/update_contact_view";
        }

        var con = contactService.getById(contactId);
        con.setId(contactId);
        con.setName(contactForm.getName());
        con.setEmail(contactForm.getEmail());
        con.setPhonenumber(contactForm.getPhoneNumber());
        con.setAddress(contactForm.getAddress());
        con.setDescription(contactForm.getDescription());
        con.setFavorite(contactForm.getFavorite());
        con.setWebsiteLink(contactForm.getWebsiteLink());
        con.setLinkedInLink(contactForm.getLinkedInLink());

        // process image:

        if (contactForm.getContactImage() != null && !contactForm.getContactImage().isEmpty()) {
            logger.info("file is not empty");
            String fileName = UUID.randomUUID().toString();
            String imageUrl = imageServiceImp.uploadImage(contactForm.getContactImage(), fileName);
            con.setCloudinaryImagePublicId(fileName);
            con.setPicture(imageUrl);
            contactForm.setPicture(imageUrl);

        } else {
            logger.info("file is empty");
        }

        var updateCon = contactService.update(con);
        logger.info("updated contact {}", updateCon);

        session.setAttribute("message", Message.builder().content("Contact Updated !!").type(MessageType.green).build());

        return "redirect:/user/contact/view/" + contactId;
    }

}
