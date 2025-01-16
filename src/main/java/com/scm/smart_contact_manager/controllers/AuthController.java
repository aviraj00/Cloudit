package com.scm.smart_contact_manager.controllers;


import com.scm.smart_contact_manager.entities.User;
import com.scm.smart_contact_manager.helper.Message;
import com.scm.smart_contact_manager.helper.MessageType;
import com.scm.smart_contact_manager.repositories.UserRepo;
import com.scm.smart_contact_manager.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepo userRepo;

    @GetMapping("/verify-email")
    public  String vrifiedEmail(@RequestParam("token") String token, HttpSession session){
       User user= userService.getUserByToken(token);
        if (user!=null){
            if (user.getEmailTokem().equals(token)){
                user.setEmailVerified(true);
                user.setEnabled(true);
                userRepo.save(user);
                session.setAttribute("message",Message.builder().content("Email Is Verified :: Please Login Again").type(MessageType.green).build());

                return "success_page";
            }
            return "error_page";
        }
        session.setAttribute("message",Message.builder().content("Fail To Verify Email :: Verification Code is MisMatch").type(MessageType.red).build());
        return "error_page";
    }

}
