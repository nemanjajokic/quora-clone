package io.neca.quoraclone.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.neca.quoraclone.exception.CustomException;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtUtil {

    // In seconds
    @Value("${token.jwt.expiration}")
    @Getter
    private long jwtExpiration;

    private final SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // Get user name from token
    public String getUsername(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
    }

    /*
    // Get all claims from token
    private Claims getAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }
    */

    // Validate token
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch(JwtException | IllegalArgumentException ex) {
            throw new CustomException("Expired or invalid JWT token");
        }
    }

    // Generate token for specific user with username
    public String GenerateTokenWithUsername(String username) {
        Map<String, Object> claims = new HashMap<>();

        return createToken(claims, username);
    }

    // Create token
    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plusSeconds(jwtExpiration)))
                .signWith(key).compact();
    }

    // Get the token from HTTP servlet request
    public String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        if(bearerToken != null && bearerToken.startsWith("Bearer "))
            return bearerToken.substring(7);

        return bearerToken;
    }

}
