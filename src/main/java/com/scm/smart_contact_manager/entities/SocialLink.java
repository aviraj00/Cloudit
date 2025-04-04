package com.scm.smart_contact_manager.entities;


import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class SocialLink {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   private long id;
   private String link;
   private String title;

    @ManyToOne
    private Contact contacts;
}
