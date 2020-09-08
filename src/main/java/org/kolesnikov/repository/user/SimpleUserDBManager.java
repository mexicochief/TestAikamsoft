package org.kolesnikov.repository.user;

import org.kolesnikov.exception.DbException;
import org.kolesnikov.model.User;
import org.kolesnikov.query.QueryExecutor;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SimpleUserDBManager implements UserDBManager {
    private final DataSource dataSource;

    public SimpleUserDBManager(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public User put(User user) {
        return null;
    }

    @Override
    public List<User> get(QueryExecutor queryExecutor) {
        return queryExecutor.runScript(dataSource);
    }

    @Override
    public User getById(long id) {
        return null;
    }

    @Override
    public User update(User user) {
        return null;
    }

    @Override
    public void delete(long id) {

    }
}
