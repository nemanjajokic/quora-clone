package io.neca.quoraclone.service;

import io.neca.quoraclone.dao.VerificationTokenRepository;
import io.neca.quoraclone.dto.LoginRequest;
import io.neca.quoraclone.dto.RegistrationRequest;
import io.neca.quoraclone.model.User;
import io.neca.quoraclone.dao.UserRepository;
import io.neca.quoraclone.model.VerificationEmail;
import io.neca.quoraclone.model.VerificationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.UUID;

@Service
public class AuthService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    VerificationTokenRepository verificationTokenRepository;
    @Autowired
    private VerificationEmailService emailService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Value("${verification.link.prefix}")
    private String verificationLinkPrefix;

    public void login(LoginRequest loginRequest) {
//        User user = userRepository.findByUsername(loginRequest.getUsername());
//        if(user.isVerified())
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
    }

    @Transactional
    public void signUp(RegistrationRequest registrationRequest) {
        User user = new User();
        user.setUsername(registrationRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        user.setEmail(registrationRequest.getEmail());
        user.setCreated(Instant.now());
        user.setVerified(false);
        userRepository.save(user);

        String token = generateVerificationToken(user);
        emailService.sendMail(new VerificationEmail(
                "Activate your account here",
                user.getEmail(),
                "please click on the link below to activate your account: " +
                verificationLinkPrefix + token));
    }

    // Email Verification

    public void verifyAccount(String token) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token);
        verifyUser(verificationToken);
    }

    private String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setUser(user);
        verificationToken.setToken(token);
        verificationTokenRepository.save(verificationToken);

        return token;
    }

    @Transactional
    private void verifyUser(VerificationToken verificationToken) {
        String username = verificationToken.getUser().getUsername();
        User user = userRepository.findByUsername(username);
        user.setVerified(true);
        userRepository.save(user);
    }

}
