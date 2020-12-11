package io.neca.quoraclone.security;

import io.neca.quoraclone.dao.RefreshTokenRepository;
import io.neca.quoraclone.exception.CustomException;
import io.neca.quoraclone.model.RefreshToken;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class RefreshTokenUtil {

    @Autowired
    private RefreshTokenRepository repository;

    @Value("${token.refresh.expiration}")
    @Getter
    private Long expiration;

    public RefreshToken generateToken() {
        String token = UUID.randomUUID().toString();
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(token);
        refreshToken.setCreated(Instant.now());

        return repository.save(refreshToken);
    }

    public void validateToken(String token) {
        repository.findByToken(token).orElseThrow(() -> new CustomException("Invalid refresh token"));
        // Check token expiration
         // Optional<RefreshToken> token1 = repository.findByToken(token);
         // if(token1.isPresent()) {
         //     int comparison = token1.get().getCreated().plusSeconds(expiration).compareTo(Instant.now());
         //     return comparison > 0;
         // } else {
         //     return false;
         // }
    }

    public void deleteToken(String token) {
        repository.deleteByToken(token);
    }

}
