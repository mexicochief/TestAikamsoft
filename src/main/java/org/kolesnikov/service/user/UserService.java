package org.kolesnikov.service.user;

import org.kolesnikov.dto.UserDto;
import org.kolesnikov.query.UserQuery;

import java.util.List;

public interface UserService {

    UserDto put(UserDto userDto);

    List<UserDto> get(UserQuery userQuery);

    UserDto getById(long id);

    UserDto update(long id, UserDto userDto);

    void delete(long id);
}
