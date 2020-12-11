package io.neca.quoraclone.security;

import io.neca.quoraclone.dao.RefreshTokenRepository;
import io.neca.quoraclone.exception.CustomException;
import io.neca.quoraclone.model.RefreshToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@Transactional
public class RefreshTokenUtil {

    @Autowired
    private RefreshTokenRepository repository;

    public RefreshToken generateToken() {
        String token = UUID.randomUUID().toString();
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(token);
        refreshToken.setCreated(Instant.now());

        return repository.save(refreshToken);
    }

    public void validateToken(String token) {
        repository.findByToken(token).orElseThrow(() -> new CustomException("Invalid refresh token"));
    }

    public void deleteToken(String token) {
        repository.deleteByToken(token);
    }

}
