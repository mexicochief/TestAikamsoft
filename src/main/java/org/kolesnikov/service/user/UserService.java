package org.kolesnikov.service.user;

import org.kolesnikov.dto.UserDto;
import org.kolesnikov.query.QueryExecutor;

import java.util.List;

public interface UserService {

    UserDto put(UserDto userDto);

    List<UserDto> get(QueryExecutor queryManager);

    UserDto getById(long id);

    UserDto update(long id, UserDto userDto);

    void delete(long id);
}
