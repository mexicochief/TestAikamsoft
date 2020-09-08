package org.kolesnikov.resolver;

import com.fasterxml.jackson.databind.JsonNode;
import org.kolesnikov.Criterias;
import org.kolesnikov.query.ProductPurchaseCountQueryExecutor;
import org.kolesnikov.query.QueryExecutor;

import java.util.ArrayList;
import java.util.List;

public class ProductCountExecutorFactory implements ExecutorFactory {
    private final List<String> criterias;

    public ProductCountExecutorFactory() {
        criterias = new ArrayList<>();
        criterias.add(Criterias.PRODUCT_NAME.getValue());
        criterias.add(Criterias.MIN_TIMES.getValue());
    }

    @Override
    public boolean hasSameType(List<String> names) {
        return criterias.containsAll(names);
    }

    @Override
    public QueryExecutor create(JsonNode criteria) {
        return new ProductPurchaseCountQueryExecutor(criteria.get(Criterias.PRODUCT_NAME.getValue()).asText(), criteria.get(Criterias.MIN_TIMES.getValue()).asLong());
    }
}
