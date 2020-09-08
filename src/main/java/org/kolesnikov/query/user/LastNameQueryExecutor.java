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

public class LastNameQueryExecutor implements QueryExecutor {
    private final String QUERY = "select first_name,last_name " +
            "from store.users " +
            "where last_name like ?";

    private final String lastName;
    private Map<String, String> criteria;

    public LastNameQueryExecutor(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String getSqlQuery() {
        return QUERY;
    }

    @Override
    public List<User> runScript(DataSource dataSource) {
        List<User> users = new ArrayList<>();
        try (final PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(QUERY)) {
            preparedStatement.setString(1, lastName);
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
            criteria.put("lastName", lastName);
        }
        return criteria;
    }

}
