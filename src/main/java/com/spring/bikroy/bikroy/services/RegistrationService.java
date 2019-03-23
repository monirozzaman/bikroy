package com.spring.bikroy.bikroy.services;

import com.spring.bikroy.bikroy.domain.model.CodeVarification;
import com.spring.bikroy.bikroy.domain.model.RegistrationUser;
import com.spring.bikroy.bikroy.domain.repository.CodeVarificationRepository;
import com.spring.bikroy.bikroy.domain.repository.RegistrationUserRepository;
import com.spring.bikroy.bikroy.dto.request.CodeVarificationRequest;
import com.spring.bikroy.bikroy.dto.request.LoginRequest;
import com.spring.bikroy.bikroy.dto.request.RegistrationRequest;
import com.spring.bikroy.bikroy.dto.response.ResponseProfile;
import javassist.NotFoundException;
import netscape.security.ForbiddenTargetException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
public class RegistrationService {
    private final RegistrationUserRepository registrationUserController;
    private final CodeVarificationRepository codeVarificationRepository;
    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${email.from.address}")
    private String fromAddress;

    public RegistrationService(RegistrationUserRepository registrationUserController, CodeVarificationRepository codeVarificationRepository) {
        this.registrationUserController = registrationUserController;
        this.codeVarificationRepository = codeVarificationRepository;
    }


    public String createUser(RegistrationRequest registrationRequest) throws MessagingException {
        String id = UUID.randomUUID().toString();
        Random rand = new Random();
        int verificationCode = rand.nextInt(5000000);
        RegistrationUser registrationUser = new RegistrationUser();
        registrationUser.setId(id);
        registrationUser.setName(registrationRequest.getName());
        registrationUser.setUsername(registrationRequest.getUsername());
        registrationUser.setAddress(registrationRequest.getAddress());
        registrationUser.setEmail(registrationRequest.getEmail());
        registrationUser.setPassword(registrationRequest.getPassword());
        registrationUser.setPhoneNo(registrationRequest.getPhoneNo());
        registrationUser.setCheckVerification(false);
        registrationUserController.save(registrationUser);
        sendMailMultipart(registrationRequest.getEmail(), verificationCode, null, id);
        return id;
    }

    public void sendMailMultipart(String toEmail, int code, File file, String userId) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        CodeVarification codeVarification = new CodeVarification();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom(fromAddress);
        helper.setTo(toEmail);
        helper.setSubject("Security");
        helper.setText("Code is:" + code);
        codeVarification.setId(userId);
        codeVarification.setVerificationCode(code);
        codeVarificationRepository.save(codeVarification);
        if (file != null) {
            helper.addAttachment(file.getName(), file);
        }
        javaMailSender.send(mimeMessage);
    }

    public ResponseProfile getProfileData(String userId) {


        Optional<RegistrationUser> registrationUser = registrationUserController.findByUsername(userId);
        if (!registrationUser.isPresent()) {
            try {
                throw new NotFoundException("Username Not Found");
            } catch (NotFoundException e) {
                e.printStackTrace();
            }
        }
        ResponseProfile responseProfile = new ResponseProfile();
        responseProfile.setName(registrationUser.get().getName());
        responseProfile.setUsername(registrationUser.get().getUsername());
        responseProfile.setAddress(registrationUser.get().getAddress());
        responseProfile.setPhoneNo(registrationUser.get().getPhoneNo());
        responseProfile.setEmail(registrationUser.get().getEmail());


        return responseProfile;
    }

    public String login(LoginRequest loginRequest) {
        Optional<RegistrationUser> registrationUserOptional =
                registrationUserController.findUserByEmailAndPassword(loginRequest.getEmail(), loginRequest.getPassword());
        if (!registrationUserOptional.isPresent()) {
            throw new ForbiddenTargetException("UserName or Password wrong");
        }
        return registrationUserOptional.get().getUsername();
    }

    public boolean checkVarifivcationCode(String regId, CodeVarificationRequest codeVarificationRequest) {
        Optional<CodeVarification> codeVarificationOptional = codeVarificationRepository.findById(regId);
        if (!codeVarificationOptional.isPresent()) {
            try {
                throw new NotFoundException("Registration Failed");
            } catch (NotFoundException e) {
                e.printStackTrace();
            }
        } else {
            if (codeVarificationOptional.get().getVerificationCode() == codeVarificationRequest.getCode()) {
                Optional<RegistrationUser> registrationUser = registrationUserController.findById(regId);
                if (registrationUser.isPresent()) {
                    registrationUser.get().setCheckVerification(true);
                    registrationUserController.save(registrationUser.get());
                    return true;
                }
            }
        }
        return false;
    }
}
