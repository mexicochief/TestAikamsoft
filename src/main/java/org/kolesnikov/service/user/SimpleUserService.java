package org.kolesnikov.service.user;

import org.kolesnikov.dto.UserDto;
import org.kolesnikov.exception.DbException;
import org.kolesnikov.model.User;
import org.kolesnikov.query.user.UserQueryExecutor;
import org.kolesnikov.repository.user.UserDBManager;
import org.kolesnikov.service.user.converter.UserConverter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SimpleUserService implements UserService {
    private final UserDBManager userDBManager;
    private final UserConverter userConverter;

    public SimpleUserService(UserDBManager userDBManager, UserConverter userConverter) {
        this.userDBManager = userDBManager;
        this.userConverter = userConverter;
    }

    @Override
    public UserDto put(UserDto userDto) {
        final User user = userConverter.convert(userDto);
        final Optional<User> optionalUser = userDBManager.put(user);
        if (optionalUser.isEmpty()) {
            throw new DbException("Empty user");
        }
        return userConverter.convert(optionalUser.get());
    }

    @Override
    public List<UserDto> get(UserQueryExecutor queryExecutor) {

        final List<User> users = userDBManager.get(queryExecutor);
        return users
                .stream()
                .map(userConverter::convert)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto getById(long id) {
        final Optional<User> optionalUser = userDBManager.getById(id);
        if (optionalUser.isEmpty()) {
            throw new DbException("Empty user");
        }
        return userConverter.convert(optionalUser.get());
    }

    @Override
    public UserDto update(long id, UserDto userDto) {
        final Optional<User> optionalUser = userDBManager.update(id, userConverter.convert(userDto));
        if (optionalUser.isEmpty()) {
            throw new DbException("Empty user");
        }
        return userConverter.convert(optionalUser.get());
    }

    @Override
    public void delete(long id) {
        userDBManager.delete(id);
    }

}
