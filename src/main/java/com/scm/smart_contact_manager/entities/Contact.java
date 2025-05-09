package com.scm.smart_contact_manager.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Contact {
    @Id
    private String id;
    private String name;
    private String email;
    private String phonenumber;
    private String address;
    private String picture;
    @Column(length = 10000)
    private String description;
    private boolean favorite=false;
    private String websiteLink;
    private String linkedInLink;

    private String  cloudinaryImagePublicId;

  //  private List<String> socialLinks=new ArrayList<>();
    @ManyToOne
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "contacts",cascade = CascadeType.ALL,fetch = FetchType.EAGER,orphanRemoval = true)
    private List<SocialLink> links=new ArrayList<>();

}
