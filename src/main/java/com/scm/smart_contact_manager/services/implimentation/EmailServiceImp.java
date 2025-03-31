package com.scm.smart_contact_manager.services.implimentation;

import com.scm.smart_contact_manager.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImp implements EmailService {
    @Autowired
    private JavaMailSender emailSender;
    @Value("${spring.mail.properties.domain_name}")
    private String from;

    @Override
    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage simpleMailMessage=new SimpleMailMessage();
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(body);
        simpleMailMessage.setFrom(from);
        emailSender.send(simpleMailMessage);
    }

    @Override
    public void sendEmailWithHtml() {

    }

    @Override
    public void sendEmailWithAttachment() {

    }
}
