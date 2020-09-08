package org.kolesnikov.query;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public interface QueryExecutor {
    String getSqlQuery();

    ResultSet execute(PreparedStatement preparedStatement) throws SQLException;

    Map<String,String> getCriteria();
}
