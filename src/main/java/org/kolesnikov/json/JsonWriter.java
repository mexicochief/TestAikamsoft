package org.kolesnikov.json;

import com.google.gson.JsonObject;

public interface JsonWriter {

    void write(JsonObject jsonObject, String fileName);
}