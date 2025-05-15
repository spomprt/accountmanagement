package ru.app.accountmanagement.mapper;

import org.springframework.stereotype.Component;
import ru.app.accountmanagement.dto.UserDto;
import ru.app.accountmanagement.model.User;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Component
public class UserMapper {

    public UserDto toDto(User user) {
        if (user == null) {
            return null;
        }

        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        // Don't map password for security reasons
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());

        // Map createdAt from LocalDateTime to OffsetDateTime
        if (user.getCreatedAt() != null) {
            userDto.setCreatedAt(OffsetDateTime.of(user.getCreatedAt(), ZoneOffset.UTC));
        }

        return userDto;
    }

    public User toEntity(UserDto userDto) {
        if (userDto == null) {
            return null;
        }

        User user = new User();
        user.setId(userDto.getId());
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());

        return user;
    }

    public void updateEntityFromDto(UserDto userDto, User user) {
        if (userDto == null || user == null) {
            return;
        }

        if (userDto.getUsername() != null) {
            user.setUsername(userDto.getUsername());
        }
        if (userDto.getEmail() != null) {
            user.setEmail(userDto.getEmail());
        }
        if (userDto.getPassword() != null) {
            user.setPassword(userDto.getPassword());
        }
        if (userDto.getFirstName() != null) {
            user.setFirstName(userDto.getFirstName());
        }
        if (userDto.getLastName() != null) {
            user.setLastName(userDto.getLastName());
        }
    }
}
