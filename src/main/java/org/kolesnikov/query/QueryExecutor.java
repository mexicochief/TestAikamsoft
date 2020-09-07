package org.kolesnikov.query;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface QueryExecutor {
    String getSqlQuery();

    ResultSet execute(PreparedStatement preparedStatement) throws SQLException;
}
