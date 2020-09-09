package org.kolesnikov.resolver.factory;

import com.fasterxml.jackson.databind.JsonNode;
import org.kolesnikov.query.user.UserQueryExecutor;

import java.util.List;

public interface ExecutorFactory {
    boolean hasSameType(List<String> names);

    UserQueryExecutor create(JsonNode criteria);
}
