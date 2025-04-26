package com.analia.config;



import com.analia.web.serializer.BigIntegerSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import io.quarkus.jackson.ObjectMapperCustomizer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.Produces;

import java.math.BigInteger;

//@ApplicationScoped
public class JacksonConfiguration implements ObjectMapperCustomizer {

    @Override
    public void customize(ObjectMapper objectMapper) {
        SimpleModule module = new SimpleModule();
        module.addSerializer(BigInteger.class, new BigIntegerSerializer());
        objectMapper.registerModule(module);
    }

    @Produces
    @ApplicationScoped
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        // Custom configuration for the objectMapper, if needed.
        SimpleModule module = new SimpleModule();
        module.addSerializer(BigInteger.class, new BigIntegerSerializer());
        objectMapper.registerModule(module);
        return objectMapper;
    }
}
