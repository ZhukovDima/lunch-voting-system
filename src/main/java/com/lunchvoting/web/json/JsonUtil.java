package com.lunchvoting.web.json;

import com.fasterxml.jackson.core.JsonProcessingException;

import static com.lunchvoting.web.json.JacksonObjectMapper.getMapper;

public class JsonUtil {

    public static <T> String writeValue(T obj) {
        try {
            return getMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Invalid write to JSON:\n'" + obj + "'", e);
        }
    }
}
