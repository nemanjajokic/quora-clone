package io.neca.quoraclone.dao;

import io.neca.quoraclone.model.InvalidJwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class InvalidJwtRepository {

    @Autowired
    RedisTemplate<Integer, String> template;

    public void saveToken(InvalidJwt token) {
        template.opsForValue().set(token.getUserId(), token.getToken());
    }

    public String findById(int id) {
        return template.opsForValue().get(id);
    }

}
