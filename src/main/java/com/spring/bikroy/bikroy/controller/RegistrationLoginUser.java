package com.spring.bikroy.bikroy.controller;

import com.spring.bikroy.bikroy.dto.request.RegistrationRequest;
import com.spring.bikroy.bikroy.dto.response.ResponseIdentity;
import com.spring.bikroy.bikroy.services.RegistrationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;

@RestController
@RequestMapping("/user")
public class RegistrationLoginUser {
    private final RegistrationService registrationService;

    public RegistrationLoginUser(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping("/")
    public ResponseIdentity createUser(@RequestBody RegistrationRequest registrationRequest) throws MessagingException {
        return registrationService.createUser(registrationRequest);
    }
}
