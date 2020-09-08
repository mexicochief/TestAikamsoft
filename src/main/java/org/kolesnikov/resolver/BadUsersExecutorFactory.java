package org.kolesnikov.resolver;

import com.fasterxml.jackson.databind.JsonNode;
import org.kolesnikov.Criterias;
import org.kolesnikov.query.user.BadUsersQueryExecutor;
import org.kolesnikov.query.QueryExecutor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class BadUsersExecutorFactory implements ExecutorFactory {
    private final List<Criterias> criterias;

    public BadUsersExecutorFactory() {
        criterias = new ArrayList<>();
        criterias.add(Criterias.BAD_CUSTOMERS);
    }


    @Override
    public boolean hasSameType(List<String> names) {
        return criterias
                .stream()
                .map(Criterias::getValue)
                .collect(Collectors.toList())
                .equals(names);
    }

    @Override
    public QueryExecutor create(JsonNode criteria) {
        return new BadUsersQueryExecutor(criteria.get(Criterias.BAD_CUSTOMERS.getValue()).asLong());
    }
}
