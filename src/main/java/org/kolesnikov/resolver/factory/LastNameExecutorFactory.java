package org.kolesnikov.resolver.factory;

import com.fasterxml.jackson.databind.JsonNode;
import org.kolesnikov.query.user.LastNameQueryExecutor;
import org.kolesnikov.query.user.UserQueryExecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LastNameExecutorFactory implements ExecutorFactory {
    private final List<Criterias> criterias;

    public LastNameExecutorFactory() {
        criterias = new ArrayList<>();
        criterias.add(Criterias.LAST_NAME);
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
    public UserQueryExecutor create(JsonNode criteria) {
        return new LastNameQueryExecutor(criteria.get(Criterias.LAST_NAME.getValue()).asText());
    }
}
