package org.kolesnikov.resolver;

import com.fasterxml.jackson.databind.JsonNode;
import org.kolesnikov.Criterias;
import org.kolesnikov.query.user.PurchaseSumIntervalQueryExecutor;
import org.kolesnikov.query.QueryExecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PurchaseSumIntervalExecutorFactory implements ExecutorFactory {
    private final List<Criterias> criterias;//todo

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
    public QueryExecutor create(JsonNode criteria) {
        final long minSum = criteria.get(Criterias.MIN_EXPENSES.getValue()).asLong();
        final long maxSum = criteria.get(Criterias.MAX_EXPENSES.getValue()).asLong();
        return new PurchaseSumIntervalQueryExecutor(minSum, maxSum);
    }
}
