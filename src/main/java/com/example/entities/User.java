package com.example.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

/**
 * Created by Eric on 4/12/17.
 */
@Entity
@Data
@Table(name = "users")
public class User {

    private String firstName;

    private String lastName;

    private String emailAddress;

    @Id
    private UUID userKey;

    private boolean emailVerified;

}




