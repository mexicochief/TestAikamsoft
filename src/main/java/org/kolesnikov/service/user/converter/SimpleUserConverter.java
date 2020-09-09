package org.kolesnikov.service.user.converter;

import org.kolesnikov.dto.UserDto;
import org.kolesnikov.model.User;

public class SimpleUserConverter implements UserConverter {
    @Override
    public User convert(UserDto userDto) {
        return new User(null, userDto.getFirstName(), userDto.getLastName());
    }

    @Override
    public UserDto convert(User user) {
        return new UserDto(user.getFirstName(), user.getLastName());
    }
}
