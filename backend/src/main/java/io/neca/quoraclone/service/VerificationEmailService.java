package io.neca.quoraclone.service;

import io.neca.quoraclone.model.VerificationEmail;
import org.apache.logging.log4j.message.SimpleMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class VerificationEmailService {

    @Autowired
    private TemplateEngine templateEngine;
    @Autowired
    private JavaMailSender sender;

    // remove this method and thymeleaf template
    public String createEmail(String message) {
        Context context = new Context();
        context.setVariable("message", message);

        return templateEngine.process("verification-email", context);
    }

    public void sendMail(VerificationEmail email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("quoraclonetest@neca.com");
        message.setSubject(email.getSubject());
        message.setTo(email.getRecipient());
        message.setText(email.getBody());
        sender.send(message);
    }

}
