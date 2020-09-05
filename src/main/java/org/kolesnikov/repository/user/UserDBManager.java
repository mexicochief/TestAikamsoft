package org.kolesnikov.repository.user;

import org.kolesnikov.model.User;
import org.kolesnikov.query.UserQuery;

import java.util.List;

public interface UserDBManager {

    User put(User user);

    List<User> get(UserQuery userQuery);

    User getById(long id);

    User update(User user);

    void delete(long id);
}
