package io.neca.quoraclone.dao;

import io.neca.quoraclone.model.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Integer> {
    
    VerificationToken findByToken(String token);
}
