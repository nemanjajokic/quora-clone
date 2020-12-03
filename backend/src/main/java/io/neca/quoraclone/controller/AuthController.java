package io.neca.quoraclone.controller;

import io.neca.quoraclone.dto.AuthenticationResponse;
import io.neca.quoraclone.dto.LoginRequest;
import io.neca.quoraclone.dto.RegistrationRequest;
import io.neca.quoraclone.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    // Log in
    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    // Sign up
    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody RegistrationRequest registrationRequest) {
        authService.signUp(registrationRequest);

        return new ResponseEntity<>("User Registered Successfully", HttpStatus.OK);
    }

    // Verification link
    @GetMapping("/accountVerification/{token}")
    public ResponseEntity<String> verification(@PathVariable String token) {
        authService.verifyAccount(token);

        return new ResponseEntity<>("Account Activated Successfully", HttpStatus.OK);
    }

}
