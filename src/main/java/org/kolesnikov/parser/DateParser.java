package org.kolesnikov.parser;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.kolesnikov.exception.ParseException;

import java.sql.Date;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Locale;
import java.util.Map;

public class DateParser {
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("uuuu-MM-dd", Locale.US)
            .withResolverStyle(ResolverStyle.STRICT);
    private final Logger logger;
    private final Gson gson;

    public DateParser(Logger logger, Gson gson) {
        this.logger = logger;
        this.gson = gson;
    }


    public Date getProperty(String name, JsonNode jsonNode) {
        final JsonNode dateNode = jsonNode.get(name);
        if (dateNode == null) {
            final String message = "property " + name + " not found";
            logger.error(gson.toJson(Map.of("error", message)));
            throw new ParseException(message);
        }
        try {
            dateFormatter.parse(dateNode.asText());
        } catch (DateTimeParseException e) {
            logger.error(gson.toJson(Map.of("error", e.getMessage())));
            throw new ParseException(e.getMessage());
        }
        return Date.valueOf(dateNode.asText());
    }
}
