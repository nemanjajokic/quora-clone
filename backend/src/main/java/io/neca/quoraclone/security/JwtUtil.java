package io.neca.quoraclone.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.neca.quoraclone.CustomException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil {

//    @Value("${jwt.secret}")
//    private String secretKey;

    // In Seconds
    @Value("${jwt.expiration}")
    private long jwtExpiration;

    private final SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

//    protected void init() {
//        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
//    }

    // Get User Name From Token
    public String getUsername(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
    }

    // Get All Claims From Token
    private Claims getAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    // Validate Token
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch(JwtException | IllegalArgumentException ex) {
            throw new CustomException("Expired or invalid JWT token");
        }
    }

    // Generate Token For Specific User
    private String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();

        return createToken(claims, userDetails.getUsername());
    }

    // Create Token
    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject)
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plusSeconds(jwtExpiration)))
                .signWith(key).compact();
    }

//    private SecretKey getKey() {
//        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
//        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
//    }

}
