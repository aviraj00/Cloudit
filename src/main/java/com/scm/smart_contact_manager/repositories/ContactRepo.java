package com.scm.smart_contact_manager.repositories;

import com.scm.smart_contact_manager.entities.Contact;
import com.scm.smart_contact_manager.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepo extends JpaRepository<Contact,String> {

    List<Contact> findByUser(User user);

    @Query("SElECT c from Contact c WHERE c.user.userId=:userId")
    List<Contact> findByUserId(@Param("userId") String userId);
}
