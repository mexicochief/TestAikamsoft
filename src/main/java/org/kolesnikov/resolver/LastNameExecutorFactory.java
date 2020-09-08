package org.kolesnikov.resolver;

import com.fasterxml.jackson.databind.JsonNode;
import org.kolesnikov.Criterias;
import org.kolesnikov.query.BadUsersQueryExecutor;
import org.kolesnikov.query.LastNameQueryExecutor;
import org.kolesnikov.query.QueryExecutor;

import java.util.ArrayList;
import java.util.List;

public class LastNameExecutorFactory implements ExecutorFactory {
    private final List<String> criterias;

    public LastNameExecutorFactory() {
        criterias = new ArrayList<>();
        criterias.add(Criterias.LAST_NAME.getValue());
    }

    @Override
    public boolean hasSameType(List<String> names) {
        return criterias.containsAll(names);
    }

    @Override
    public QueryExecutor create(JsonNode criteria) {
        return new LastNameQueryExecutor(criteria.get(Criterias.LAST_NAME.getValue()).asText());
    }
}
