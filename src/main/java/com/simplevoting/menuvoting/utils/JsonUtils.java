package com.simplevoting.menuvoting.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonUtils {

    private static ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    public static <T> String wrightValue(T obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Invalid write to JSON:\n'" + obj + "'", e);
        }
    }

    public static <T> T readValue(String json, Class<T> tClass) {
        try {
            return objectMapper.readValue(json, tClass);
        } catch (IOException e) {
            throw new IllegalArgumentException("Invalid read from JSON:\n'" + json + "'", e);
        }
    }
}
