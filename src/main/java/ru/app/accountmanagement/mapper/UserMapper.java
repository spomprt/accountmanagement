package ru.app.accountmanagement.mapper;

import org.springframework.stereotype.Component;
import ru.app.accountmanagement.dto.UserDto;
import ru.app.accountmanagement.model.User;

@Component
public class UserMapper {

    public User toEntity(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        return user;
    }

    public UserDto toDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        return dto;
    }

}
