package com.learning.flink.common.serialization;

import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;
import java.util.Map;

public class MapDeserializer implements Deserializer<Map<String, Object>> {

    private final ObjectMapper objectMapper = new ObjectMapper();


    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> deserialize(String topic, byte[] data) {
        try {
            return objectMapper.readValue(data, Map.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
