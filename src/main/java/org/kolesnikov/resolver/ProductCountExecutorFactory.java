package org.kolesnikov.resolver;

import com.fasterxml.jackson.databind.JsonNode;
import org.kolesnikov.Criterias;
import org.kolesnikov.query.QueryExecutor;
import org.kolesnikov.query.user.ProductPurchaseCountQueryExecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProductCountExecutorFactory implements ExecutorFactory {
    private final List<Criterias> criterias;

    public ProductCountExecutorFactory() {
        this.criterias = new ArrayList<>();
        criterias.add(Criterias.PRODUCT_NAME);
        criterias.add(Criterias.MIN_TIMES);
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
        final String productName = Criterias.PRODUCT_NAME.getValue();
        final String minTimes = Criterias.MIN_TIMES.getValue();
        return new ProductPurchaseCountQueryExecutor(
                criteria.get(productName).asText(),
                criteria.get(minTimes).asLong());
    }
}
