package com.example.entities;

import javax.persistence.*;

@Entity
@Table(name = "messages")
public class CongressMessages {

    @Id
    @GeneratedValue
    private long id;  //will be a hidden field in the messages

    @Column(nullable = false)
    String userEmail; //takes the users email that he signs up with, will be automatically implemented in the message to the congressman

    @Column(nullable = false)
    String subject;

    @Column(nullable = false)
    String textBody;

    public CongressMessages(String userEmail, String subject, String textBody) {
        this.userEmail = userEmail;
        this.subject = subject;
        this.textBody = textBody;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getTextBody() {
        return textBody;
    }

    public void setTextBody(String textBody) {
        this.textBody = textBody;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTextbody() {
        return textBody;
    }

    public void setTextbody(String textBody) {
        this.textBody = textBody;
    }

    public CongressMessages() {
    }
}