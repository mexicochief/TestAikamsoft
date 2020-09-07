package org.kolesnikov.query;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BadUsersQueryExecutor implements QueryExecutor {
    @Override
    public String getSqlQuery() {
        return null;
    }

    @Override
    public ResultSet execute(PreparedStatement preparedStatement) throws SQLException {
        return null;
    }
}
