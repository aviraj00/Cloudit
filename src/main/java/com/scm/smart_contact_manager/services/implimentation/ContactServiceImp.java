package com.scm.smart_contact_manager.services.implimentation;

import com.scm.smart_contact_manager.entities.Contact;
import com.scm.smart_contact_manager.entities.User;
import com.scm.smart_contact_manager.helper.ResourceNotFoundException;
import com.scm.smart_contact_manager.repositories.ContactRepo;
import com.scm.smart_contact_manager.services.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ContactServiceImp implements ContactService {
    @Autowired
    private ContactRepo contactRepo;

    @Override
    public Contact save(Contact contact) {
        String contactid= UUID.randomUUID().toString();
        contact.setId(contactid);
        return contactRepo.save(contact);
    }

    @Override
    public Contact update(Contact contact) {
       var oldContact= contactRepo.findById(contact.getId()).orElseThrow(() -> new ResourceNotFoundException("Contact Not Found"));
        oldContact.setName(contact.getName());
        oldContact.setEmail(contact.getEmail());
        oldContact.setPhonenumber(contact.getPhonenumber());
        oldContact.setAddress(contact.getAddress());

        oldContact.setDescription(contact.getDescription());
        oldContact.setFavorite(contact.isFavorite());
        oldContact.setPicture(contact.getPicture());
        oldContact.setWebsiteLink(contact.getWebsiteLink());
        oldContact.setCloudinaryImagePublicId(contact.getCloudinaryImagePublicId());
        oldContact.setLinkedInLink(contact.getLinkedInLink());
        return contactRepo.save(oldContact);
    }

    @Override
    public List<Contact> getAll() {
        return contactRepo.findAll();
    }

    @Override
    public Contact getById(String id) {
        return contactRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
    }

    @Override
    public void delete(String id) {
    var contact= contactRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
        contactRepo.delete(contact);
    }

    @Override
    public Page<Contact> searchByName(String name, int page, int size, String sortBy, String order,User user) {
        Sort sort=order.equals("desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
        var pageable=PageRequest.of(page,size,sort);

        return contactRepo.findByUserAndNameContaining(user,name,pageable);
    }

    @Override
    public Page<Contact> searchByEmail(String email, int page, int size, String sortBy, String order,User user) {
        Sort sort=order.equals("desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
        var pageable=PageRequest.of(page,size,sort);

        return contactRepo.findByUserAndEmailContaining(user,email,pageable);
    }

    @Override
    public Page<Contact> searchByPhoneNumber(String phoneNumber, int page, int size, String sortBy, String order,User user ) {
        Sort sort=order.equals("desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
        var pageable=PageRequest.of(page,size,sort);

        return contactRepo.findByUserAndPhonenumberContaining(user,phoneNumber,pageable);
    }


    @Override
    public List<Contact> getByUserId(String userId) {
       return contactRepo.findByUserId(userId);

    }

    @Override
    public Page<Contact> getByUser(User user,int page,int size,String sortBy,String direction) {
        Sort sort=direction.equals("desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
        var pageable= PageRequest.of(page,size,sort);

        return contactRepo.findByUser(user,pageable);
    }
}
