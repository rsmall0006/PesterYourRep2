package com.example.repositories;

import com.example.entities.User;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.Properties;

/**
 * Created by mfahrner on 4/17/17.
 */

public class EmailSender {
    private class SMTPAuthenticator extends Authenticator
    {
        public PasswordAuthentication getPasswordAuthentication()
        {
            return new PasswordAuthentication("pesteryourrep@gmail.com", "wordpass123");
        }
    }

    public void createAndSendEmailMessage(ArrayList<?> messageContents, User user) throws MessagingException {
        Email email = new Email();
        email.setRecipient(user.getEmailAddress());
        email.setSender("pesteryourrep@gmail.com");
        email.setTitle("Welcome to Pester Your Rep");
        email.setBody("Thank you for signing up with Pester Your Rep.");
        sendEmailMessage(email, user);
    }


    public void sendEmailMessage(Email email, User user) throws MessagingException {

        // Get system properties
        Properties props = System.getProperties();
        props = new Properties();
        props.put("mail.smtp.user", "pesteryourrep@gmail.com");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.starttls.enable","true");
        props.put("mail.smtp.debug", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.socketFactory.port", "587");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");


        SMTPAuthenticator auth = new SMTPAuthenticator();
        Session session = Session.getInstance(props, auth);
        session.setDebug(false);

        MimeMessage msg = new MimeMessage(session);
        msg.setText("test");
        msg.setSubject("test");
        msg.setFrom("pesteryourrep@gmail.com");
        msg.addRecipient(Message.RecipientType.TO, new InternetAddress(user.getEmailAddress()));

        Transport transport = session.getTransport("smtps");
        transport.connect("smtp.gmail.com", 465, "username", "password");
        transport.sendMessage(msg, msg.getAllRecipients());
        transport.close();

    }

}