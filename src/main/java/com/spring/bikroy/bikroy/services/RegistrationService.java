package com.spring.bikroy.bikroy.services;

import com.spring.bikroy.bikroy.domain.model.CodeVarification;
import com.spring.bikroy.bikroy.domain.model.RegistrationUser;
import com.spring.bikroy.bikroy.domain.repository.CodeVarificationRepository;
import com.spring.bikroy.bikroy.domain.repository.RegistrationUserController;
import com.spring.bikroy.bikroy.dto.request.RegistrationRequest;
import com.spring.bikroy.bikroy.dto.response.ResponseIdentity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Random;
import java.util.UUID;

@Service
public class RegistrationService {
    private final RegistrationUserController registrationUserController;
    private final CodeVarificationRepository codeVarificationRepository;
    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${email.from.address}")
    private String fromAddress;

    public RegistrationService(RegistrationUserController registrationUserController, CodeVarificationRepository codeVarificationRepository) {
        this.registrationUserController = registrationUserController;
        this.codeVarificationRepository = codeVarificationRepository;
    }


    public ResponseIdentity createUser(RegistrationRequest registrationRequest) throws MessagingException {
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
        registrationUser.setCheckVerification(false);
        registrationUserController.save(registrationUser);
        sendMailMultipart(registrationRequest.getEmail(), verificationCode, null, id);
        return new ResponseIdentity(id);
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
}
