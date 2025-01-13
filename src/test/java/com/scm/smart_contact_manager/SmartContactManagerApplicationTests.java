package com.scm.smart_contact_manager;

import com.scm.smart_contact_manager.services.EmailService;
import com.scm.smart_contact_manager.services.implimentation.EmailServiceImp;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SmartContactManagerApplicationTests {

    @Test
    void contextLoads() {
    }
@Autowired
private EmailService service;
    @Test
    void sendEmailTest(){
        service.sendEmail("avi00aviraj@gmail.com","Testing","Testing new Site");
    }

}
