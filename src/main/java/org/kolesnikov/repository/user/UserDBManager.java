package org.kolesnikov.repository.user;

import org.kolesnikov.model.User;
import org.kolesnikov.query.user.UserQueryExecutor;

import java.util.List;

public interface UserDBManager {

    User put(User user);

    List<User> get(UserQueryExecutor queryExecutor);

    User getById(long id);

    User update(User user);

    void delete(long id);
}
