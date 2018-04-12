package com.simplevoting.menuvoting.utils.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;

import static com.simplevoting.menuvoting.utils.json.JsonObjectMapper.getObjectMapper;

public class JsonUtils {

    static {
        getObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    public static <T> String wrightValue(T obj) {
        try {
            return getObjectMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Invalid write to JSON:\n'" + obj + "'", e);
        }
    }

    public static <T> T readValue(String json, Class<T> tClass) {
        try {
            return getObjectMapper().readValue(json, tClass);
        } catch (IOException e) {
            throw new IllegalArgumentException("Invalid read from JSON:\n'" + json + "'", e);
        }
    }
}
