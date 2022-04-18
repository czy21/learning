package com.learning.flink.common.serilization;

import lombok.SneakyThrows;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.core.type.TypeReference;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class MapDeserializer implements Deserializer<Map<String, Object>> {

    private ObjectMapper objectMapper = new ObjectMapper();


    @SneakyThrows
    @Override
    public Map<String, Object> deserialize(String topic, byte[] data) {
        return objectMapper.readValue(data, new TypeReference<Map<String, Object>>() {
        });
    }
}
