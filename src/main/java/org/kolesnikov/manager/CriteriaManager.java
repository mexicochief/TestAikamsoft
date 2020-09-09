package org.kolesnikov.manager;

import com.fasterxml.jackson.databind.JsonNode;
import org.kolesnikov.query.user.UserQueryExecutor;
import org.kolesnikov.resolver.factory.ExecutorFactory;
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
    public List<UserQueryExecutor> getQueryExecutors(JsonNode criterias) {
        List<UserQueryExecutor> queryExecutors = new ArrayList<>();
        for (JsonNode criteria : criterias) {
            List<String> names = new ArrayList<>();
            final Iterator<String> stringIterator = criteria.fieldNames();
            stringIterator.forEachRemaining(names::add);
            final ExecutorFactory factory = nodeResolver.resolve(names);
            queryExecutors.add(factory.create(criteria));
        }
        return queryExecutors;
    }
}
