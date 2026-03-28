package com.sky.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
@Slf4j
@Configuration
public class RedisConfiguration {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        log.info("=== 开始创建 RedisTemplate ===");

        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();

        // 1. 设置连接工厂
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        // 2. 设置 Key 的序列化器 (通常用 String)
        redisTemplate.setKeySerializer(new StringRedisSerializer());

        // 3. 【关键修复】设置 Value 的序列化器
        // 方案 A: 如果只存字符串，用 StringRedisSerializer
        // redisTemplate.setValueSerializer(new StringRedisSerializer());

        // 方案 B: 【推荐】如果要存对象，自动转为 JSON，使用 GenericJackson2JsonRedisSerializer
        // 需要确保引入了 spring-boot-starter-data-redis 或 jackson 依赖
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());

        // 4. 设置 Hash Key 和 Hash Value 的序列化器 (防止操作 Hash 时报错)
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());

        // 5. 初始化后验证配置 (可选)
        redisTemplate.afterPropertiesSet();

        log.info("=== RedisTemplate 创建完成，序列化器已配置 ===");
        return redisTemplate;
    }
}