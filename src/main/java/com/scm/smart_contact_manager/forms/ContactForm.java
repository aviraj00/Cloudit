package com.scm.smart_contact_manager.forms;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ContactForm {
    @NotBlank(message = "Name is Required")
    private String name;
    @NotBlank(message = "Email is Required")
    @Email(message = "Email is Required")
    private String email;
    @NotBlank(message = "Required Number")
    @Pattern(regexp = "^[0-9]{10}$",message = "Invalid Number")
    private String phoneNumber;

    private String address;
    private String description;
    private String websiteLink;
    private String linkedInLink;

    private Boolean favorite;

    private MultipartFile profilePic;
}
