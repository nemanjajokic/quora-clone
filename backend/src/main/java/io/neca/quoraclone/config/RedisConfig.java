package io.neca.quoraclone.config;

import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
@EnableConfigurationProperties(RedisProperties.class)
@EnableCaching
public class RedisConfig {

    @Bean
    RedisTemplate<Integer, String> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<Integer, String> template = new RedisTemplate<Integer, String>();
        template.setConnectionFactory(connectionFactory);

        return template;
    }

}
