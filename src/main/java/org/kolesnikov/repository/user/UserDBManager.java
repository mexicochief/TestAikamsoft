package org.kolesnikov.repository.user;

import org.kolesnikov.model.User;
import org.kolesnikov.query.user.UserQueryExecutor;

import java.util.List;
import java.util.Optional;

public interface UserDBManager {

    Optional<User> put(User user);

    List<User> get(UserQueryExecutor queryExecutor);

    Optional<User> getById(long id);

    Optional<User> update(long id, User user);

    void delete(long id);
}
