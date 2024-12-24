package com.world.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);

        // 设置key的序列化器，使用StringRedisSerializer将key序列化为字符串
        redisTemplate.setKeySerializer(new StringRedisSerializer());

        // 设置value的序列化器，使用GenericJackson2JsonRedisSerializer将对象序列化为JSON
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());

        // 设置hash key的序列化器，使用StringRedisSerializer序列化hash的key
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());

        // 设置hash value的序列化器，使用Jackson2JsonRedisSerializer将hash值序列化为JSON
        redisTemplate.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));

        return redisTemplate;
    }
}
