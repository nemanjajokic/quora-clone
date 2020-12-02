package io.neca.quoraclone.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.neca.quoraclone.exception.CustomException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
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
    public String generateToken(Authentication authentication) {
        Map<String, Object> claims = new HashMap<>();

        return createToken(claims, authentication.getPrincipal().toString());   // Experiment With Authentication Instead Of UserDetails
    }

    // Generate Token For Specific User With Username
    public String GenerateTokenWithUsername(String username) {
        Map<String, Object> claims = new HashMap<>();

        return createToken(claims, username);
    }

    // Create Token
    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject)
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plusSeconds(jwtExpiration)))
                .signWith(key).compact();
    }

    // Get The Token From HTTP Servlet Request
    public String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        if(bearerToken != null && bearerToken.startsWith("Bearer "))
            return bearerToken.substring(7);

        return bearerToken;
    }

//    private SecretKey getKey() {
//        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
//        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
//    }

}
