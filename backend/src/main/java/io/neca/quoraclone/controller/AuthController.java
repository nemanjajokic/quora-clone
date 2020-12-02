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

    // Log In
    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);

//        return new ResponseEntity<>("You're logged in", HttpStatus.OK);
    }

    // Sign Up
    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody RegistrationRequest registrationRequest) {
        authService.signUp(registrationRequest);

        return new ResponseEntity<>("User Registered Successfully", HttpStatus.OK);
    }

    // Verification Link
    @GetMapping("/accountVerification/{token}")
    public ResponseEntity<String> verification(@PathVariable String token) {
        authService.verifyAccount(token);

        return new ResponseEntity<>("Account Activated Successfully", HttpStatus.OK);
    }

}
