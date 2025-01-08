package com.scm.smart_contact_manager.services;

import com.scm.smart_contact_manager.entities.Contact;
import com.scm.smart_contact_manager.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ContactService {

    Contact save(Contact contact);


    Contact update(Contact contact);

    List<Contact> getAll();

    Contact getById(String id);

    void delete(String id);

    Page<Contact> searchByName(String name,int page,int size,String sortBy,String order,User user);
    Page<Contact> searchByEmail(String email,int page,int size,String sortBy,String order,User user);
    Page<Contact> searchByPhoneNumber(String phoneNumber,int page,int size,String sortBy,String order,User user);

    List<Contact> getByUserId(String userId);

    Page<Contact> getByUser(User user, int page,int size,String sortField,String sortDirection);

}
