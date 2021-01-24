package io.neca.quoraclone.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthenticationResponse {
    private String jwtToken;
    private String username;
    private String imageUri;
    private String refreshToken;
    private Instant expiration;
}
