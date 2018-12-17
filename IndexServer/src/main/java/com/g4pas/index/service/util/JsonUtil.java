package com.g4pas.index.service.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Tika does not handle Json file so extract separately
 */
public class JsonUtil {

    private static final ThreadLocal<ObjectMapper> om = ThreadLocal.withInitial(() -> {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
                               false);
        return objectMapper;
    });

    private static ObjectMapper getObjectMapper() {
        return om.get();
    }

    public static Map<String, Object> toMap(final String json) throws IOException {

        return getObjectMapper().readValue(json,
                                           new TypeReference<HashMap<String, Object>>() {});
    }

    public static String asString (Object map) throws JsonProcessingException {
        return getObjectMapper().writeValueAsString(map);
    }

}
