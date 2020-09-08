package org.kolesnikov.resolver;

import com.fasterxml.jackson.databind.JsonNode;
import org.kolesnikov.Criterias;
import org.kolesnikov.query.BadUsersQueryExecutor;
import org.kolesnikov.query.QueryExecutor;

import java.util.ArrayList;
import java.util.List;

public class BadUsersExecutorFactory implements ExecutorFactory {
    private final List<String> criterias;

    public BadUsersExecutorFactory() {
        criterias = new ArrayList<>();
        criterias.add(Criterias.BAD_CUSTOMERS.getValue());
    }

    @Override
    public boolean hasSameType(List<String> names) {
        return criterias.containsAll(names);
    }

    @Override
    public QueryExecutor create(JsonNode criteria) {
        return new BadUsersQueryExecutor(criteria.get(Criterias.BAD_CUSTOMERS.getValue()).asLong());
    }
}
