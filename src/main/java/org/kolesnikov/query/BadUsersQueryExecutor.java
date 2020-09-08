package org.kolesnikov.query;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class BadUsersQueryExecutor implements QueryExecutor {
    private final String QUERY = "select first_name, last_name " +
            "from (select users.id userId, first_name, last_name, sum(cost) overalCost " +
            "      from store.users " +
            "               left join store.purchases purch on users.id = purch.user_id " +
            "               left join store.products prod on purch.product_id = prod.id " +
            "      group by first_name, last_name, userId) as userWithOveralSum " +
            "order by overalCost nulls first " +
            "limit ?;";

    private final long limit;
    private Map<String, String> criteria;

    public BadUsersQueryExecutor(long limit) {
        this.limit = limit;
    }

    @Override
    public String getSqlQuery() {
        return QUERY;
    }

    @Override
    public ResultSet execute(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setLong(1, limit);
        return preparedStatement.executeQuery();
    }

    @Override
    public Map<String, String> getCriteria() {
        if (criteria == null) {
            criteria = new HashMap<>();
            criteria.put("badCustomers", String.valueOf(limit));
        }
        return criteria;
    }
}
