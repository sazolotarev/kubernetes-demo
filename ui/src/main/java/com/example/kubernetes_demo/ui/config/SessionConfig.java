package com.example.kubernetes_demo.ui.config;

import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.serializer.JacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.security.jackson.SecurityJacksonModules;

import tools.jackson.databind.json.JsonMapper;

@Configuration
public class SessionConfig implements BeanClassLoaderAware {

    private ClassLoader classLoader;

    @Bean
    RedisSerializer<Object> springSessionDefaultRedisSerializer() {
        return new JacksonJsonRedisSerializer<>(objectMapper(), Object.class);
    }

    private JsonMapper objectMapper() {
        return JsonMapper.builder().addModules(SecurityJacksonModules.getModules(this.classLoader)).build();
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }
}
