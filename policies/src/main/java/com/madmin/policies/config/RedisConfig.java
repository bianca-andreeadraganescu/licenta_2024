package com.madmin.policies.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.madmin.policies.object.Role;
//import com.madmin.policies.object.User;
import com.madmin.policies.object.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;


@Configuration
@EnableRedisRepositories
@ComponentScan("com.madmin.policies")
public class RedisConfig {
    @Bean
    public JedisConnectionFactory createConnectionFactory() {
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setHostName("localhost");
        configuration.setPort(6379);
        return new JedisConnectionFactory(configuration);
    }

    @Bean(name = "template_role")
    public RedisTemplate<String, Role> redisTemplateRole() {
        RedisTemplate<String, Role> template = new RedisTemplate<>();
        template.setConnectionFactory(createConnectionFactory()); // Change the database index if needed
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        Jackson2JsonRedisSerializer<Role> serializer = new Jackson2JsonRedisSerializer<>(Role.class);
        template.setValueSerializer(serializer);
        template.setHashValueSerializer(serializer);
        template.afterPropertiesSet();
        return template;
    }
    @Bean(name = "template_user")
    public RedisTemplate<String, User> redisTemplateUser() {
        RedisTemplate<String, User> template = new RedisTemplate<>();
        template.setConnectionFactory(createConnectionFactory()); // Change the database index if needed
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        Jackson2JsonRedisSerializer<User> serializer = new Jackson2JsonRedisSerializer<>(User.class);
        template.setValueSerializer(serializer);
        template.setHashValueSerializer(serializer);
        template.afterPropertiesSet();
        return template;
    }
    @Primary
    @Bean(name = "template_lab")
    public RedisTemplate<String, Object> redisTemplateLab() {
        return createRedisTemplate();
    }

    @Bean(name = "template_dev")
    public RedisTemplate<String, Object> redisTemplateDev() {
        return createRedisTemplate();
    }

    @Bean(name = "template_exam")
    public RedisTemplate<String, Object> redisTemplateExam() {
        return createRedisTemplate();
    }

    @Bean(name = "template_default")
    public RedisTemplate<String, Object> redisTemplateDefault() {
        return createRedisTemplate();
    }

    private RedisTemplate<String, Object> createRedisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(createConnectionFactory());
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());

        Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        serializer.setObjectMapper(mapper);

        template.setValueSerializer(serializer);
        template.setHashValueSerializer(serializer);
        template.afterPropertiesSet();
        return template;
    }

}

