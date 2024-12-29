package com.scm.smart_contact_manager.services;

import com.scm.smart_contact_manager.entities.Contact;
import com.scm.smart_contact_manager.entities.User;

import java.util.List;
import java.util.Optional;

public interface ContactService {

    Contact save(Contact contact);


    Contact update(Contact contact);

    List<Contact> getAll();

    Optional<Contact> getById(String id);

    void delete(String id);

    List<Contact> search(String name);

    List<Contact> getByUserId(String userId);

    List<Contact> getByUser(User user);

}
