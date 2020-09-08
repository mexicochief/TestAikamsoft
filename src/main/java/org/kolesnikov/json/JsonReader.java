package org.kolesnikov.json;

import com.fasterxml.jackson.databind.JsonNode;

public interface JsonReader  {

    JsonNode read(String path);
}
