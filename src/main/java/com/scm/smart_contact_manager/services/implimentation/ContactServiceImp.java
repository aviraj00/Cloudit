package com.scm.smart_contact_manager.services.implimentation;

import com.scm.smart_contact_manager.entities.Contact;
import com.scm.smart_contact_manager.helper.ResourceNotFoundException;
import com.scm.smart_contact_manager.repositories.ContactRepo;
import com.scm.smart_contact_manager.services.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
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
        return null;
    }

    @Override
    public List<Contact> getAll() {
        return contactRepo.findAll();
    }

    @Override
    public Optional<Contact> getById(String id) {
        return Optional.ofNullable(contactRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User Not Found")));
    }

    @Override
    public void delete(String id) {
    var contact= contactRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
        contactRepo.delete(contact);
    }

    @Override
    public List<Contact> search(String name) {
    return null;
    }

    @Override
    public List<Contact> getByUserId(String userId) {
       return contactRepo.findByUserId(userId);

    }
}
