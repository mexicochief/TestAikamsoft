package org.kolesnikov.resolver.factory;

import com.fasterxml.jackson.databind.JsonNode;
import org.kolesnikov.query.user.BadUsersQueryExecutor;
import org.kolesnikov.query.user.UserQueryExecutor;

import java.util.ArrayList;
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
    public UserQueryExecutor create(JsonNode criteria) {
        return new BadUsersQueryExecutor(criteria.get(Criterias.BAD_CUSTOMERS.getValue()).asLong());
    }
}
