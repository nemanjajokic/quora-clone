package io.neca.quoraclone.utils;

import io.neca.quoraclone.dao.RefreshTokenRepository;
import io.neca.quoraclone.exception.CustomException;
import io.neca.quoraclone.model.RefreshToken;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@Transactional
public class RefreshTokenUtil {

    @Autowired
    private RefreshTokenRepository repository;

    @Value("${token.refresh.expiration}")
    @Getter
    private Long expiration;

    public boolean isRefreshTokenExpired(String token) {
        RefreshToken refreshToken = repository.findByToken(token).orElseThrow(() -> new CustomException("Invalid refresh token"));
        return refreshToken.getExpiration().isBefore(Instant.now());
    }

    public RefreshToken generateToken() {
        String token = UUID.randomUUID().toString();
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(token);
        refreshToken.setExpiration(Instant.now().plusSeconds(expiration));

        return repository.save(refreshToken);
    }

    public void deleteToken(String token) {
        repository.deleteByToken(token);
    }

}
