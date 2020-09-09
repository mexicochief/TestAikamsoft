package org.kolesnikov.json;

import com.google.gson.JsonObject;

import java.io.FileWriter;

public interface JsonWriter {

    void write(JsonObject jsonObject, String fileName);
}