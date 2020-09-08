package org.kolesnikov.query;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class PurchaseSumIntervalQueryExecutor implements QueryExecutor {
    private final String GET_BY_OVERALL_SUM = "select first_name, last_name " +
            "from (select users.id userId, first_name, last_name, sum(cost) overallCost " +
            "from store.users " +
            "         left join store.purchases purch on users.id = purch.user_id " +
            "         left join store.products prod on purch.product_id = prod.id " +
            "group by first_name, last_name, userId) as userWithOverallSum where overallCost > ? and overallCost < ?";

    private Map<String, String> criteria;
    private final long minExpenses;
    private final long maxExpenses;

    public PurchaseSumIntervalQueryExecutor(long minSum, long maxSum) {
        this.minExpenses = minSum;
        this.maxExpenses = maxSum;
    }

    @Override
    public String getSqlQuery() {
        return GET_BY_OVERALL_SUM;
    }

    @Override
    public ResultSet execute(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setLong(1, minExpenses);
        preparedStatement.setLong(2, maxExpenses);
        return preparedStatement.executeQuery();
    }

    @Override
    public Map<String, String> getCriteria() {
        if (criteria == null) {
            criteria = new HashMap<>();
            criteria.put("minExpenses", String.valueOf(minExpenses));
            criteria.put("maxExpenses", String.valueOf(maxExpenses));
        }
        return criteria;
    }
}
