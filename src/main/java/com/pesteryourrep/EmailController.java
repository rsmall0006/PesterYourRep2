package com.pesteryourrep;

import com.pesteryourrep.entities.CongressMessage;
import com.pesteryourrep.entities.User;
import com.pesteryourrep.models.Email;
import com.pesteryourrep.models.SignupRequest;
import com.pesteryourrep.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.UUID;

@Slf4j
@Controller
public class EmailController {

    private final EmailSender sender;

    private final UserRepository userRepository;

    private final PasswordEncoder encoder;


    ArrayList<CongressMessage> messagelist = new ArrayList<CongressMessage>();


    @Inject
    public EmailController(EmailSender sender, UserRepository userRepository, PasswordEncoder encoder) {
        this.sender = sender;
        this.userRepository = userRepository;
        this.encoder = encoder;
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String splashPage(Model model, HttpSession session) {
        // model.addAttribute() //what attribute should I add here to the splash page?
        return "";

    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String login(HttpSession session, String emailAddress, String password) {

        User byEmailAddress = userRepository.findByEmailAddress(emailAddress);
        if (byEmailAddress == null) {
            return ""; //this is a placeholder and we will have to return something for an error condition if the user doesnt exist (not valid)
        } if (encoder.matches(password, byEmailAddress.getPassword())) {
            return "true";  //must implement this
        }
            //google for spring session examples for token

        return "redirect:/index.html";
    }

    @RequestMapping(value = "/logout")  //invalidate the session
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        request.setAttribute("logout", "logout");
        return "login";
    }

    @RequestMapping(path = "/signUp", method = RequestMethod.POST)
    public String signUp(SignupRequest request) {

        final UUID userKey = UUID.nameUUIDFromBytes(request.getEmailAddress().getBytes());


        if (userRepository.exists(userKey)) {
            return "email already in use";
        }

        User user = new User();
        user.setUserKey(userKey);
        user.setEmailAddress(request.getEmailAddress());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());

        String hashedpassword = encoder.encode(request.getPassword());

        user.setPassword(hashedpassword);

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