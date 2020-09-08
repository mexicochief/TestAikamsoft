package org.kolesnikov.manager;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.JsonObject;
import org.kolesnikov.query.QueryExecutor;
import org.kolesnikov.resolver.ExecutorFactory;
import org.kolesnikov.resolver.NodeResolver;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CriteriaManager implements QueryManager {
    private final NodeResolver nodeResolver;

    public CriteriaManager(NodeResolver nodeResolver) {
        this.nodeResolver = nodeResolver;
    }

    @Override
    public List<QueryExecutor> getQueryExecutors(JsonNode criterias) {
        List<QueryExecutor> queryExecutors = new ArrayList<>();
        for (JsonNode criteria : criterias) {
            List<String> names = new ArrayList<>();
            final Iterator<String> stringIterator = criteria.fieldNames();
            stringIterator.forEachRemaining(names::add);
            final ExecutorFactory factory = nodeResolver.resolve(names);
            if (factory != null) { //todo
                queryExecutors.add(factory.create(criteria)); //todo
            }
        }
        return queryExecutors;
    }
}
