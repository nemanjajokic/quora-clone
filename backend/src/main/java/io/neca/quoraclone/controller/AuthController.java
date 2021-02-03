package io.neca.quoraclone.controller;

import io.neca.quoraclone.dto.AuthenticationResponse;
import io.neca.quoraclone.dto.LoginRequest;
import io.neca.quoraclone.dto.RefreshTokenRequest;
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

    // Sign up
    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> signUp(@RequestBody RegistrationRequest registrationRequest) {
        authService.signUp(registrationRequest);

        return new ResponseEntity<>("User Registered Successfully", HttpStatus.OK);
    }

    // Login
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public AuthenticationResponse login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    // Logout
    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.RESET_CONTENT)
    public void logout(@RequestBody RefreshTokenRequest tokenRequest) {
        authService.logout(tokenRequest);
    }

    // Verification link
    @GetMapping("/accountVerification/{token}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> verification(@PathVariable String token) {
        authService.verifyAccount(token);

        return new ResponseEntity<>("Account Activated Successfully", HttpStatus.OK);
    }

    // Refresh token
    @PostMapping("/refresh/token")
    @ResponseStatus(HttpStatus.OK)
    public AuthenticationResponse refreshToken(@RequestBody RefreshTokenRequest tokenRequest) {
        return authService.refreshToken(tokenRequest);
    }

}
