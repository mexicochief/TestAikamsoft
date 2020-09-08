package org.kolesnikov.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.kolesnikov.StoreApp;

import java.io.IOException;
import java.io.InputStream;

public class SimpleJsonReader implements JsonReader {
    @Override
    public JsonNode read(String path) {
        try (InputStream inputStream = StoreApp.class.getClassLoader().getResourceAsStream("test.json")) {
            ObjectMapper objectMapper = new ObjectMapper();
            if (inputStream != null) {
                return objectMapper.readValue(inputStream, JsonNode.class);
            }
        } catch (IOException e) {
            e.printStackTrace();//todo
        }
        return null;
    }
}
