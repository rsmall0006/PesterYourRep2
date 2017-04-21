package com.example.entities;

import lombok.Data;
import lombok.experimental.Tolerate;

import javax.persistence.*;

@Entity
@Data
@Table(name = "CONGRESSMESSAGES")
public class CongressMessage {

    @Id
    @GeneratedValue
    private long id;  //will be a hidden field in the messages

    @Column(nullable = false)
    String userEmail; //takes the users email that he signs up with, will be automatically implemented in the message to the congressman

    @Column(nullable = false)
    String subject;

    @Column(nullable = false)
    String textBody;

    @Tolerate
    public CongressMessage(String userEmail, String subject, String textBody) {
        this.userEmail = userEmail;
        this.subject = subject;
        this.textBody = textBody;
    }

}