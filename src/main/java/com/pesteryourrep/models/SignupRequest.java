package com.pesteryourrep.models;

import lombok.Data;

/**
 * Created by Eric on 4/12/17.
 */

@Data
public class SignupRequest {


    private String firstName;

    private String lastName;

    private String emailAddress;

    private String password;


}
