package com.example;

import com.example.entities.CongressMessage;
import com.example.entities.User;
import com.example.models.Email;
import com.example.models.SignupRequest;
import com.example.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    ArrayList<CongressMessage> messagelist = new ArrayList<CongressMessage>();


    @Autowired
    private UserRepository users;

    @Inject
    public EmailController(EmailSender sender, UserRepository userRepository) {
        this.sender = sender;
        this.userRepository = userRepository;

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    @RequestMapping(path = "/", method = RequestMethod.GET)
        public String splashPage(Model model, HttpSession session) {
        model.addAttribute() //what attribute should I add here to the splash page?
        return "login.html";

    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String login(HttpSession session, String emailAddress, String password, boolean emailVerified) {

        if(userRepository.findFirstByEmailAddressAndPassword(emailAddress, password, emailVerified))

    }


    @RequestMapping(value="/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        request.setAttribute("logout","logout");
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
        userRepository.save(user);

        Email email = sender.createEmail(user);

        try {
            sender.sendEmailMessage(email);
        } catch (MessagingException e1) {
            log.error("failed to send email message. user=[{}] message=[{}]", user, email, e1); //e1 if the last log is an error it will log a stack trace
        }

        return "redirect:/";
    }


//
//    //connect this to email sender, to the database i guess through our array list which will be displayed on our trendingTopics.html
//    @RequestMapping(path = "/add-message", method = RequestMethod.POST)
//    public String addMessage(HttpSession session, long id, String userEmail, String subject, String textBody) {
//        session.setAttribute(id);
//        session.setAttribute("userEmail", userEmail);
//        session.setAttribute("subject", subject);
//        session.setAttribute("textBody", textBody);
//        messagelist.add(new CongressMessage(messagelist.size() + 1, CongressMessage)); //
//        System.out.println(messagelist.size());
//        return "redirect:/";

}
