package org.kolesnikov.query.user;

import org.kolesnikov.model.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface UserQueryExecutor {
    String getSqlQuery();

    List<User> runScript(DataSource dataSource);

    Map<String, String> getCriteria();
}
