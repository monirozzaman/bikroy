package com.spring.bikroy.bikroy.controller;

import com.spring.bikroy.bikroy.dto.request.CodeVarificationRequest;
import com.spring.bikroy.bikroy.dto.request.LoginRequest;
import com.spring.bikroy.bikroy.dto.request.RegistrationRequest;
import com.spring.bikroy.bikroy.dto.response.ResponseIdentity;
import com.spring.bikroy.bikroy.dto.response.ResponseProfile;
import com.spring.bikroy.bikroy.services.RegistrationService;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class RegistrationLoginUser {
    private final RegistrationService registrationService;

    public RegistrationLoginUser(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping("/")
    public ResponseIdentity createUser(@RequestBody RegistrationRequest registrationRequest) throws MessagingException {
        return registrationService.createUser(registrationRequest);
    }

    @GetMapping("/{username}")
    public ResponseProfile getProfile(@PathVariable("username") String userId) {
        return registrationService.getProfileData(userId);
    }

    @PostMapping("/login")
    public String getProfile(@RequestBody LoginRequest loginRequest) {
        return registrationService.login(loginRequest);
    }

    @PutMapping("/check/{regId}")
    public boolean varify(@PathVariable("regId") String regId, @RequestBody CodeVarificationRequest codeVarificationRequest) {
        return registrationService.checkVarifivcationCode(regId, codeVarificationRequest);
    }
}
