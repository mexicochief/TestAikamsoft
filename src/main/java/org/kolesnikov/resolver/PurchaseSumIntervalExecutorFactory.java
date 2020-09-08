package org.kolesnikov.resolver;

import com.fasterxml.jackson.databind.JsonNode;
import org.kolesnikov.Criterias;
import org.kolesnikov.query.PurchaseSumIntervalQueryExecutor;
import org.kolesnikov.query.QueryExecutor;

import java.util.ArrayList;
import java.util.List;

public class PurchaseSumIntervalExecutorFactory implements ExecutorFactory {
    private final List<String> criterias;//todo

    public PurchaseSumIntervalExecutorFactory() {
        criterias = new ArrayList<>();
        criterias.add(Criterias.MIN_EXPENSES.getValue());
        criterias.add(Criterias.MAX_EXPENSES.getValue());
    }

    @Override
    public boolean hasSameType(List<String> names) {
        return criterias.containsAll(names);//todo
    }

    @Override
    public QueryExecutor create(JsonNode criteria) {
        return new PurchaseSumIntervalQueryExecutor(criteria.get(Criterias.MIN_EXPENSES.getValue()).asLong(),
                criteria.get(Criterias.MAX_EXPENSES.getValue()).asLong());//todo
    }
}
