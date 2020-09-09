package org.kolesnikov.resolver.factory;

import com.fasterxml.jackson.databind.JsonNode;
import org.kolesnikov.query.user.PurchaseSumIntervalQueryExecutor;
import org.kolesnikov.query.user.UserQueryExecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PurchaseSumIntervalExecutorFactory implements ExecutorFactory {
    private final List<Criterias> criterias;

    public PurchaseSumIntervalExecutorFactory() {
        criterias = new ArrayList<>();
        criterias.add(Criterias.MIN_EXPENSES);
        criterias.add(Criterias.MAX_EXPENSES);
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
        final long minSum = criteria.get(Criterias.MIN_EXPENSES.getValue()).asLong();
        final long maxSum = criteria.get(Criterias.MAX_EXPENSES.getValue()).asLong();
        return new PurchaseSumIntervalQueryExecutor(minSum, maxSum);
    }
}
