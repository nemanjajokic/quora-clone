package io.neca.quoraclone.service;

import io.neca.quoraclone.model.VerificationEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class VerificationEmailService {

    @Autowired
    private JavaMailSender sender;

    public void sendMail(VerificationEmail email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("quoraclonetest@neca.com");
        message.setSubject(email.getSubject());
        message.setTo(email.getRecipient());
        message.setText(email.getBody());
        sender.send(message);
    }

}
