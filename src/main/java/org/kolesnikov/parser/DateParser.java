package org.kolesnikov.parser;

import com.fasterxml.jackson.databind.JsonNode;

import java.sql.Date;

public class DateParser {

    public Date getProperty(String name, JsonNode jsonNode){
        return  Date.valueOf(jsonNode.get(name).asText());// todo проверить на null
    }
}
