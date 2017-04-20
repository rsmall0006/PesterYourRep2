package com.example.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

/**
 * Created by Eric on 4/12/17.
 */
@Entity
@Data
@Table(name = "users")
public class User {

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String emailAddress;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID userKey;

    @Column
    private boolean emailVerified;

    public User(String firstName, String lastName, String emailAddress, UUID userKey, boolean emailVerified) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.userKey = userKey;
        this.emailVerified = emailVerified;
    }

}




