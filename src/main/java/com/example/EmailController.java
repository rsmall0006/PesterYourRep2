package com.example;

import com.example.entities.User;
import com.example.models.SignupRequest;
import com.example.repositories.EmailSender;
import com.example.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;
import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.UUID;


@Controller
public class EmailController {

    private static final Logger logger = LoggerFactory.getLogger(EmailController.class);
    JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();



    public void send(SimpleMailMessage simpleMailMessage, MimeMailMessage mimeMailMessage) throws MailException {

        };



    @Inject
    public EmailController(UserRepository userRepository) {
            this.userRepository = userRepository;
         //I took out the notifications injection because it was doing the same thing as what I am doing below
    }

    private final UserRepository userRepository;

  //  private final Notifications notifications;


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

                //creating a user
                User user = new User("firstNameUser1", "lastNameUser1", "PesterYourRep@gmail.com", UUID.nameUUIDFromBytes(request.getEmailAddress().getBytes()), true);
                user.setFirstName(request.getFirstName());
                user.setLastName(request.getLastName());
                user.setEmailAddress(request.getEmailAddress());
                user.setUserKey(userKey);
                userRepository.save(user);

                ArrayList<String> emailInfo = new ArrayList<String>();
                emailInfo.add(user.getEmailAddress());
                emailInfo.add("An account has been created for you!");
                emailInfo.add("Here is a message");
                EmailSender newEmail = new EmailSender();
                try {
                    newEmail.createAndSendEmailMessage(emailInfo, user);
                } catch (MessagingException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            return "redirect:/";
    }
}
