package org.kolesnikov.service.user.converter;

import org.kolesnikov.dto.UserDto;
import org.kolesnikov.model.User;

public interface UserConverter {
    User convert(UserDto userDto);

    UserDto convert(User user);

}
