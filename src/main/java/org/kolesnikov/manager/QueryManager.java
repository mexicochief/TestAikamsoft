package org.kolesnikov.manager;

import com.fasterxml.jackson.databind.JsonNode;
import org.kolesnikov.query.user.UserQueryExecutor;

import java.util.List;

public interface QueryManager {


    List<UserQueryExecutor> getQueryExecutors(JsonNode jsonNode);
}
