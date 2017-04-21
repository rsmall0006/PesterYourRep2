package com.example;

import com.example.entities.User;
import com.example.models.Email;
import com.google.common.io.Resources;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Properties;


@Component
public class EmailSender {

    private final Authenticator AUTH = new Authenticator() {
        public PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication("pesteryourrep@gmail.com", "wordpass123");
        }
    };

    private static final Properties GMAIL = new Properties();

    static {
        try {
            GMAIL.load(Resources.getResource("gmail.properties").openStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Email createEmail(User user) {
        Email email = new Email();
        email.setRecipient(user.getEmailAddress());
        email.setSender("pesteryourrep@gmail.com");
        email.setTitle("Welcome to Pester Your Rep");
        email.setBody("Thank you for signing up with Pester Your Rep.");
        return email;
    }

    public void sendEmailMessage(Email email) throws MessagingException {

        Session session = Session.getInstance(GMAIL, AUTH);
        session.setDebug(false);

        MimeMessage msg = new MimeMessage(session);
        msg.setText("replace with things from the email object");
        msg.setSubject("replace with things from the email object");
        msg.setFrom(email.getSender());
        msg.addRecipient(Message.RecipientType.TO, new InternetAddress(email.getRecipient()));

        Transport transport = session.getTransport("smtps");
        transport.connect("smtp.gmail.com", 465, "username", "password");
        transport.sendMessage(msg, msg.getAllRecipients());
        transport.close();

    }

}