package ru.app.accountmanagement.controller;

import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.app.accountmanagement.dto.UserDto;
import ru.app.accountmanagement.mapper.UserMapper;
import ru.app.accountmanagement.model.User;
import ru.app.accountmanagement.service.UserService;

@RestController
@RequiredArgsConstructor
public class UserController implements UserManagementApi {

    private final UserMapper userMapper;

    private final UserService userService;

    @Override
    public ResponseEntity<UserDto> createUser(UserDto userDto) {
        User user = userMapper.toEntity(userDto);

        userService.create(user);

        UserDto response = userMapper.toDto(user);

        return ResponseEntity.status(201)
                .body(response);
    }

    @Override
    public ResponseEntity<Void> deleteUser(UUID id) {
        userService.delete(id);

        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<UserDto> getUserById(UUID id) {
        User user = userService.getById(id);

        UserDto response = userMapper.toDto(user);

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<UserDto> updateUser(UUID id, UserDto userDto) {
        User user = userService.getById(id);

        User updated = userService.update(user, userDto);

        UserDto response = userMapper.toDto(updated);

        return ResponseEntity.ok(response);
    }

}
