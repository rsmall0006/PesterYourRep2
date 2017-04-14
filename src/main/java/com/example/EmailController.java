package com.example;

import com.example.entities.User;
import com.example.models.SignupRequest;
import com.example.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.UUID;


@Controller
public class EmailController {

    private static final Logger logger = LoggerFactory.getLogger(EmailController.class);


    public void send(SimpleMailMessage simpleMailMessage) throws MailException {

    }

    @Inject
    public EmailController(UserRepository userRepository, Notifications notifications) {
        this.userRepository = userRepository;
        this.notifications = notifications;
    }

    private final UserRepository userRepository;

    private final Notifications notifications;


    @RequestMapping(path = "/signup", method = RequestMethod.POST)
    public String signup(SignupRequest request) {

        final UUID userKey = UUID.nameUUIDFromBytes(request.getEmailAddress().getBytes());

        if (userRepository.exists(userKey)) {
            return "email already in use";

        }


        //creating a user
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmailAddress(request.getEmailAddress());
        user.setUserKey(userKey);
        userRepository.save(user);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(request.getEmailAddress());
        mailMessage.setFrom(request.getEmailAddress());
        mailMessage.setSubject(request.getEmailAddress());
        mailMessage.setText("Thank you for registering for Pester Your Rep. Please click on the link below to validate your account");
        send(mailMessage);


        //this notification is probably doing the same thing as the simple mail message bloc above
        notifications.sendNotification(user);

        return "";

        }







//        //send the url for the new user to click on to validate their account
//
//        //after the user is saved into our repository we send them an email to verify that their email exists.
//
//                //do i have to implement this send method twice, again with then first send method, because it goes red when i take it out
//
//        //have the server send our user an email
////        return "Thank you for registering with Pester Your Rep.  An e-mail has been sent to your e-mail address for verification";
//
//    }
//
//
//    @RequestMapping(path = "/registration", method = RequestMethod.GET)
//    public Iterable<User> users() {
//
//       return userRepository.findAll();
//
//
//    }
//
//
//
//    //create a simpleMailMessage object
//
//}
//
//
//
////
////
    @RequestMapping(path = "/registrationSuccess", method = RequestMethod.GET)
    public String regustrationSuccess(String firstName, String lastName, String emailAddress) {

//
//        //sending a notification
//        try {
//            notifications.sendNotification(u);
//        } catch (MailException e) {
//            //catch error
//            logger.info("Error sending email" + e.getMessage());
//        }

        return "Thank you for registering with Pester your Rep";

    }

}
