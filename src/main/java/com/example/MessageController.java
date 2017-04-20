package com.example;

import com.example.entities.CongressMessages;
import com.example.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eric on 4/19/17.
 */

@Controller
public class MessageController {


    @Autowired
    MessageRepository messages;


    @RequestMapping(path = "/to-Representative", method = RequestMethod.GET) //this path should be
    public String messageHome(Model model) {
            List<CongressMessages> messageList = (ArrayList) messages.findAll();
            model.addAttribute("messages", messageList);
            return "messageList.html"; //need to tell Ross to make a messageList.html
    }

    @RequestMapping(path = "/add-message", method = RequestMethod.POST)
    public String addMessage(String subject, String messageText) {
            CongressMessages message = new CongressMessages();
            messages.save(message);
            return "redirect:/";
    }



}
