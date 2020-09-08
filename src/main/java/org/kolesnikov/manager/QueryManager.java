package org.kolesnikov.manager;

import com.fasterxml.jackson.databind.JsonNode;
import org.kolesnikov.query.QueryExecutor;

import java.util.List;

public interface QueryManager {

    List<QueryExecutor> getQueryExecutors(JsonNode jsonNode);
}
