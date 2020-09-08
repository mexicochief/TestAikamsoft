package org.kolesnikov.resolver;

import com.fasterxml.jackson.databind.JsonNode;
import org.kolesnikov.query.QueryExecutor;

import java.util.List;

public interface ExecutorFactory {
    public boolean hasSameType(List<String> names);

    QueryExecutor create(JsonNode criteria);
}
