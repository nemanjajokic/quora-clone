package io.neca.quoraclone.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@RedisHash(timeToLive = 600) // Use redis cache manager instead
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InvalidJwt implements Serializable {
    private int userId;
    private String token;
}
