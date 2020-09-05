package org.kolesnikov.service.user;

import org.kolesnikov.dto.UserDto;
import org.kolesnikov.model.User;
import org.kolesnikov.query.UserQuery;
import org.kolesnikov.repository.user.UserDBManager;
import org.kolesnikov.service.user.converter.UserConverter;

import java.util.List;
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
        return userConverter.convert(userDBManager.put(user));
    }

    @Override
    public List<UserDto> get(UserQuery userQuery) {
        final List<User> users = userDBManager.get(userQuery);
        return users
                .stream()
                .map(userConverter::convert)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto getById(long id) {
        return userConverter.convert(userDBManager.getById(id));
    }

    @Override
    public UserDto update(long id, UserDto userDto) {
        return userConverter.convert(userDBManager.update(userConverter.convert(userDto)));
    }

    @Override
    public void delete(long id) {
        userDBManager.delete(id);
    }
}
