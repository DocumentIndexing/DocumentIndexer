package com.g4pas.index.service.util;

import org.junit.Test;

import java.io.IOException;
import java.util.Map;

import static org.junit.Assert.assertNotNull;

public class JsonUtilTest {

    @Test
    public void testToMap() throws IOException {
        var testJson = "{\n" +
                "\"HelloWorld\":\"fred\"\n" +
                "}\n";
        final Map<String, Object> stringObjectMap = JsonUtil.toMap(testJson);
        assertNotNull(stringObjectMap);
    }


    @Test
    public void testToMapObject() throws IOException {
        var testJson = "{\n" +
                "\"HelloWorld\":{" +
                "   \"fred\":\"flintstone\"" +
                "}}\n";
        final Map<String, Object> stringObjectMap = JsonUtil.toMap(testJson);
        assertNotNull(stringObjectMap);
    }
}