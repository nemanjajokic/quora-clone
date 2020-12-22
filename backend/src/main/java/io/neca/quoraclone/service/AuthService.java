package io.neca.quoraclone.service;

import io.neca.quoraclone.dao.VerificationTokenRepository;
import io.neca.quoraclone.dto.AuthenticationResponse;
import io.neca.quoraclone.dto.LoginRequest;
import io.neca.quoraclone.dto.RefreshTokenRequest;
import io.neca.quoraclone.dto.RegistrationRequest;
import io.neca.quoraclone.exception.CustomException;
import io.neca.quoraclone.model.User;
import io.neca.quoraclone.dao.UserRepository;
import io.neca.quoraclone.model.VerificationEmail;
import io.neca.quoraclone.model.VerificationToken;
import io.neca.quoraclone.security.JwtUtil;
import io.neca.quoraclone.security.RefreshTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@Transactional
public class AuthService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private VerificationTokenRepository verificationTokenRepository;
    @Autowired
    private VerificationEmailService emailService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private RefreshTokenUtil refreshTokenUtil;

    @Value("${verification.link.prefix}")
    private String verificationLinkPrefix;
    @Value("${token.verification.expiration}")
    private Long verificationTokenExpiration;

    // Sign Up
    public void signUp(RegistrationRequest registrationRequest) {
        // Implement method to check if username exists in db
        // if(!userRepository.existsUserByUsername(registrationRequest.getUsername()))
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

    // Login
    public AuthenticationResponse login(LoginRequest loginRequest) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        // Authorization
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String token = jwtUtil.GenerateTokenWithUsername(loginRequest.getUsername());

        return AuthenticationResponse.builder()
                .jwtToken(token)
                .username(loginRequest.getUsername())
                .refreshToken(refreshTokenUtil.generateToken().getToken())
                .expiration(Instant.now().plusSeconds(jwtUtil.getJwtExpiration()))
                .build();
    }

    // Logout
    public void logout(RefreshTokenRequest tokenRequest) {
        refreshTokenUtil.deleteToken(tokenRequest.getToken());
        // Save JWT token in redis blacklist or whatever just to instant logout
    }

    // Refresh Token
    public AuthenticationResponse refreshToken(RefreshTokenRequest tokenRequest) {
        if(!refreshTokenUtil.isRefreshTokenExpired(tokenRequest.getToken())) {
            String token = jwtUtil.GenerateTokenWithUsername(tokenRequest.getUsername());

            return AuthenticationResponse.builder()
                    .jwtToken(token)
                    .username(tokenRequest.getUsername())
                    .refreshToken(tokenRequest.getToken())
                    .expiration(Instant.now().plusSeconds(jwtUtil.getJwtExpiration()))
                    .build();
        } else {
            throw new CustomException("Invalid or expired refresh token");
        }
    }

    public User getCurrentUser() {
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return userRepository.findByUsername(user.getUsername());
    }

    // Email Verification

    public void verifyAccount(String token) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token);
        verifyUser(verificationToken);
    }

    private boolean isVerificationTokenExpired(String token) {
        return verificationTokenRepository.findByToken(token).getExpiration().isBefore(Instant.now());
    }

    private String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setUser(user);
        verificationToken.setToken(token);
        verificationToken.setExpiration(Instant.now().plusSeconds(verificationTokenExpiration));
        verificationTokenRepository.save(verificationToken);

        return token;
    }

    private void verifyUser(VerificationToken verificationToken) {
        if(!isVerificationTokenExpired(verificationToken.getToken())) {
            String username = verificationToken.getUser().getUsername();
            User user = userRepository.findByUsername(username);
            user.setVerified(true);
            userRepository.save(user);
        }
    }

}
