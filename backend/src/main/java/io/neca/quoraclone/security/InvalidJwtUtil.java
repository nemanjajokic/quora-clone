package io.neca.quoraclone.security;

import io.neca.quoraclone.dao.InvalidJwtRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InvalidJwtUtil {

    @Autowired
    private InvalidJwtRepository repository;

    /*
    // Check for invalidated tokens in redis
    public boolean checkBlacklist(int userId, String token) {
        String invalidToken = repository.findById(userId);

        return invalidToken == null || !invalidToken.equals(token);
    }
    */
}
