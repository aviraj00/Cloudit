package com.scm.smart_contact_manager.entities;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.*;
import java.util.stream.Collectors;

@Entity(name = "user")
@Table(name="users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements UserDetails {
    @Id
    private String userId;
    @Column(name="user_name",nullable = false)
    private String name;
    @Column(unique = true,nullable = false)
    private String email;
    @Getter(AccessLevel.NONE)
    private String password;
    @Column(length = 1000)
    private String about;
    @Column(length = 1000)
    private String profilPic;
    private String phoneNumber;
    @Getter(value = AccessLevel.NONE)
    private boolean enabled=false;
    private  boolean emailVerified=false;
    private boolean phoneVerified=false;
    @Enumerated(value = EnumType.STRING)
    private Provider provider=Provider.SELF ;
    private String providerId;
@OneToMany(mappedBy = "user" ,cascade = CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval = true)
 private List<Contact> contacts=new ArrayList<>();

@ElementCollection(fetch = FetchType.EAGER)
private List<String> roleList=new ArrayList<>();

private String emailTokem;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // list of roles[USER,ADMIN]
        // Collection of SimpGrantedAuthority[roles{ADMIN,USER}]
        Collection<SimpleGrantedAuthority> roles = roleList.stream().map(role -> new SimpleGrantedAuthority(role))
                .collect(Collectors.toList());
        return roles;
    }
    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    @Override
    public String getPassword() {
        return this.password;
    }


}
