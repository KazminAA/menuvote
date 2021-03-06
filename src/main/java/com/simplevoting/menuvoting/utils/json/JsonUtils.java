package com.simplevoting.menuvoting.utils.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.simplevoting.menuvoting.utils.json.JsonObjectMapper.getObjectMapper;

public class JsonUtils {

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

    private static <T> Map<String, Object> mapWithIgnore(T obj, String[] propsToIgnore) {
        Map<String, Object> map = getObjectMapper().convertValue(obj, new TypeReference<Map<String, Object>>() {
        });
        for (String prop : propsToIgnore) {
            map.remove(prop);
        }
        return map;
    }

    public static <T> String wrightIgnoreProps(T obj, String... propsToIgnore) {
        Map<String, Object> map = mapWithIgnore(obj, propsToIgnore);
        return wrightValue(map);
    }

    public static <T> String wrightIgnoreProps(Collection<T> collection, String... propsToIgnore) {
        List<Map<String, Object>> list = collection.stream()
                .map(obj -> mapWithIgnore(obj, propsToIgnore))
                .collect(Collectors.toList());
        return wrightValue(list);
    }

    public static <T> String wrightAdditionProps(T obj, String prop, Object value) {
        return wrightAdditionProps(obj, Collections.singletonMap(prop, value));
    }

    public static <T> String wrightAdditionProps(T obj, Map<String, Object> addProps) {
        Map<String, Object> map = getObjectMapper().convertValue(obj, new TypeReference<Map<String, Object>>() {
        });
        map.putAll(addProps);
        return wrightValue(map);
    }

}
