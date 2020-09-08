package org.kolesnikov.query;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class LastNameQueryExecutor implements QueryExecutor {
    private final String GET_BY_LAST_NAME = "select first_name,last_name " +
            "from store.users " +
            "where last_name like ?";

    private final String lastName;
    private Map<String, String> criteria;

    public LastNameQueryExecutor(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String getSqlQuery() {
        return GET_BY_LAST_NAME;
    }

    @Override
    public ResultSet execute(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, lastName);
        return preparedStatement.executeQuery();
    }

    @Override
    public Map<String, String> getCriteria() {
        if (criteria == null) {
            criteria = new HashMap<>();
            criteria.put("lastName", lastName);
        }
        return criteria;
    }

}
