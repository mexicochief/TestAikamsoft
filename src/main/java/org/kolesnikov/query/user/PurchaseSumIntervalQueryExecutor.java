package org.kolesnikov.query.user;

import org.kolesnikov.exception.DbException;
import org.kolesnikov.model.User;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PurchaseSumIntervalQueryExecutor implements UserQueryExecutor {
    private final String QUERY = "select first_name, last_name " +
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
        return QUERY;
    }

    @Override
    public List<User> runScript(DataSource dataSource) {
        List<User> users = new ArrayList<>();
        try (final PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(QUERY)) {
            preparedStatement.setLong(1, minExpenses);
            preparedStatement.setLong(2, maxExpenses);
            final ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                final String firstName = resultSet.getString(1);
                final String lastName = resultSet.getString(2);
                users.add(new User(null, firstName, lastName));
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage(), e.getCause());
        }
        return users;
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
