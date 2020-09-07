package org.kolesnikov.query;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PurchaseSumIntervalQueryExecutor implements QueryExecutor {
    private final String GET_BY_OVERALL_SUM = "select first_name, last_name " +
            "from (select users.id userId, first_name, last_name, sum(cost) overallCost " +
            "from store.users " +
            "         left join store.purchases purch on users.id = purch.user_id " +
            "         left join store.products prod on purch.product_id = prod.id " +
            "group by first_name, last_name, userId) as userWithOverallSum where overallCost > ? and overallCost < ?";
    private final long minSum;
    private final long maxSum;

    public PurchaseSumIntervalQueryExecutor(long minSum, long maxSum) {
        this.minSum = minSum;
        this.maxSum = maxSum;
    }

    @Override
    public String getSqlQuery() {
        return GET_BY_OVERALL_SUM;
    }

    @Override
    public ResultSet execute(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setLong(1, minSum);
        preparedStatement.setLong(2, maxSum);
        return preparedStatement.executeQuery();
    }
}
