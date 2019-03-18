package com.spring.bikroy.bikroy.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationUser extends BasicEntity {

    private String name;
    private String username;
    private String address;
    private String email;
    private String password;
    private int phoneNo;
    private boolean checkVerification;
}
