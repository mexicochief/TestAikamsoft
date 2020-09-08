package org.kolesnikov.json;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class SimpleJsonWriter implements JsonWriter {
    private final Gson gson;

    public SimpleJsonWriter(Gson gson) {
        this.gson = gson;
    }


    @Override
    public void write(JsonObject jsonObject, String fileName) {
        try (final FileWriter fileWriter = new FileWriter(fileName)) {
            gson.toJson(jsonObject, fileWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
