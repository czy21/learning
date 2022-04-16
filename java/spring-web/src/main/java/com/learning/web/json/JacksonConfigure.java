package com.learning.web.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class JacksonConfigure {

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return builder -> {
            builder.serializationInclusion(JsonInclude.Include.NON_NULL);
            builder.serializerByType(LocalDateTime.class, LocalDateTimeSerializer.INSTANCE);
            builder.deserializerByType(LocalDateTime.class, LocalDateTimeDeserializer.INSTANCE);
            builder.deserializerByType(String.class, stringStdScalarDeserializer());
            builder.featuresToEnable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        };
    }

    public JsonDeserializer<String> stringStdScalarDeserializer() {
        return new JsonDeserializer<>() {
            @Override
            public String deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
                return StringUtils.trim(p.getValueAsString());
            }
        };
    }
}
