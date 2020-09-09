package org.kolesnikov.repository.user;

import org.kolesnikov.exception.DbException;
import org.kolesnikov.model.User;
import org.kolesnikov.query.user.UserQueryExecutor;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

public class SimpleUserDBManager implements UserDBManager {
    private final DataSource dataSource;

    private final String PUT_QUERY = "insert into store.users(first_name, last_name) " +
            "values (?, ?);";
    private final String GET_BY_ID_QUERY = "select * from store.users where id = ?";
    private final String UPDATE_QUERY = "update store.users set " +
            "first_name = ?, " +
            "last_name = ? " +
            "where id = ?";
    private final String DELETE_QUERY = "delete from store.users where id = ?";

    public SimpleUserDBManager(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<User> put(User user) {
        try (final PreparedStatement preparedStatement
                     = dataSource.getConnection().prepareStatement(PUT_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            final ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            final long id = resultSet.getLong(1);
            return Optional.of(new User(id, user.getFirstName(), user.getLastName()));
        } catch (SQLException e) {
            throw new DbException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public List<User> get(UserQueryExecutor queryExecutor) {
        return queryExecutor.runScript(dataSource);
    }

    @Override
    public Optional<User> getById(long id) {
        try (final PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(GET_BY_ID_QUERY)) {
            preparedStatement.setLong(1, id);
            final ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                return Optional.empty();
            }
            final long userId = resultSet.getLong(1);
            final String firstName = resultSet.getString(2);
            final String lastName = resultSet.getString(3);
            return Optional.of(new User(userId, firstName, lastName));
        } catch (SQLException e) {
            throw new DbException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public Optional<User> update(long id, User user) {
        try (final PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(UPDATE_QUERY)) {
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setLong(3, id);
            final ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                return Optional.empty();
            }
            final long userId = resultSet.getLong(1);
            final String firstName = resultSet.getString(2);
            final String lastName = resultSet.getString(3);
            return Optional.of(new User(userId, firstName, lastName));
        } catch (SQLException e) {
            throw new DbException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public void delete(long id) {
        try (final PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(DELETE_QUERY)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e.getMessage(), e.getCause());
        }
    }
}
