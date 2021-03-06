package org.kolesnikov.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.kolesnikov.exception.ParseException;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

public class SimpleJsonReader implements JsonReader {
    private final Logger logger;
    private final Gson gson;

    public SimpleJsonReader(Logger logger, Gson gson) {
        this.logger = logger;
        this.gson = gson;
    }

    @Override
    public JsonNode read(String path) {
        try (FileInputStream inputStream = new FileInputStream(path)) {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(inputStream, JsonNode.class);
        } catch (IOException e) {
            logger.error(gson.toJson(Map.of("error",e.getMessage())));
            throw new ParseException(e.getMessage());
        }
    }
}
