package com.example;


import com.example.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * Created by Eric on 4/12/17.
 */
@Service
public class Notifications {

    private JavaMailSender javaMailSender;

    @Autowired
    public Notifications(JavaMailSender javaMailSender){
        this.javaMailSender = javaMailSender;

    }

    public void sendNotification(User user) throws MailException {

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmailAddress());
        mailMessage.setFrom(user.getEmailAddress());
        mailMessage.setSubject("testSubject");
        mailMessage.setText("testMessage");

        javaMailSender.send(mailMessage);

    }



}
