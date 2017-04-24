package com.pesteryourrep.entities;

import lombok.Data;
import lombok.experimental.Tolerate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

/**
 * Created by Eric on 4/12/17.
 */
@Entity
@Data
@Table(name = "USERS")
public class User {

    @Id
    private UUID userKey;

    @Column
    private String emailAddress;

    @Column
    private String password;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private boolean emailVerified;

   @Tolerate
    public User(UUID userKey, String emailAddress, String password, String firstName, String lastName) {
        this.userKey = userKey;
        this.emailAddress = emailAddress;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;

    }
}




