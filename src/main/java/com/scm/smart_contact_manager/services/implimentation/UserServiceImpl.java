package com.scm.smart_contact_manager.services.implimentation;


import com.scm.smart_contact_manager.entities.User;
import com.scm.smart_contact_manager.helper.AppConstants;
import com.scm.smart_contact_manager.helper.ResourceNotFoundException;
import com.scm.smart_contact_manager.repositories.UserRepo;
import com.scm.smart_contact_manager.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private Logger logger= LoggerFactory.getLogger(this.getClass());
    @Override
    public User saveUser(User user) {
        String userId= UUID.randomUUID().toString();
        user.setUserId(userId);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        user.setRoleList(List.of(AppConstants.ROLE_USER));
        logger.info(user.getProvider().toString());



        User savedUser = userRepo.save(user);
        return savedUser;
    }

    @Override
    public Optional<User> getUserById(String id) {
        return userRepo.findById(id);
    }

    @Override
    public Optional<User> updateUser(User user) {
       User user2= userRepo.findById(user.getUserId()).orElseThrow(()->new ResourceNotFoundException("User not found"));
       user2.setName(user.getName());
        user2.setEmail(user.getEmail());
        user2.setPassword(user.getPassword());
        user2.setPhoneNumber(user.getPhoneNumber());
        user2.setAbout(user.getAbout());
        user2.setProfilPic(user.getProfilPic());
        user2.setEnabled(user.isEnabled());
        user2.setEmailVerified(user.isEmailVerified());
        user2.setPhoneVerified(user.isPhoneVerified());
        user2.setProvider(user.getProvider());
        user2.setProviderId(user.getProviderId());

        User save=userRepo.save(user2);
        return Optional.ofNullable(save);
    }

    @Override
    public void deleteUser(String id) {
        User user2= userRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("User not found"));
           userRepo.delete(user2);
    }

    @Override
    public boolean isUserExist(String userid) {
        User user2= userRepo.findById(userid).orElse(null);

        return user2!=null ? true:false;
    }

    @Override
    public boolean isUserExistByEmail(String email) {
        User user2 = userRepo.findByEmail(email).orElse(null);
        return user2!=null ? true:false;

    }

    @Override
    public List<User> getAllUser() {
        return userRepo.findAll();
    }
    @Override
    public User getUserByEmail(String email) {
        return userRepo.findByEmail(email).orElse(null);

    }
}
