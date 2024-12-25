package com.scm.smart_contact_manager.forms;


import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ContactForm {

    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private String description;
    private String websiteLink;
    private String linkedInLink;
    private Boolean favorite;

    private MultipartFile profilePic;
}
