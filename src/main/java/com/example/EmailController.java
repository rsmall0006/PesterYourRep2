package com.example;

import com.example.entities.User;
import com.example.models.Email;
import com.example.models.SignupRequest;
import com.example.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;
import javax.mail.MessagingException;
import java.util.UUID;

@Slf4j
@Controller
public class EmailController {

    private final EmailSender sender;

    private final UserRepository userRepository;

    @Inject
    public EmailController(EmailSender sender, UserRepository userRepository) {
        this.sender = sender;
        this.userRepository = userRepository;

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String login(String firstName, String lastName, String emailAddress) {


        return "login.html";

    }

    @RequestMapping(path = "/signup", method = RequestMethod.POST)
    public String signup(SignupRequest request) {

        final UUID userKey = UUID.nameUUIDFromBytes(request.getEmailAddress().getBytes());

        if (userRepository.exists(userKey)) {
            return "email already in use";
        }

        User user = new User();
        user.setUserKey(userKey);
        user.setEmailAddress(request.getEmailAddress());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        userRepository.save(user);

        Email email = sender.createEmail(user);

        try {
            sender.sendEmailMessage(email);
        } catch (MessagingException e1) {
            log.error("failed to send email message. user=[{}] message=[{}]", user, email, e1); //e1 if the last log is an error it will log a stack trace
        }

        return "redirect:/";
    }
}
