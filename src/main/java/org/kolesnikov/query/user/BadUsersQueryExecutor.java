package org.kolesnikov.query.user;


import org.kolesnikov.exception.DbException;
import org.kolesnikov.model.User;
import org.kolesnikov.query.QueryExecutor;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    public List<User> runScript(DataSource dataSource) {
        List<User> users = new ArrayList<>();
        try (final PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(QUERY)) {
            preparedStatement.setLong(1, limit);
            final ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                final String firstName = resultSet.getString(1);
                final String lastName = resultSet.getString(2);
                users.add(new User(firstName, lastName));
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
            criteria.put("badCustomers", String.valueOf(limit));
        }
        return criteria;
    }
}
