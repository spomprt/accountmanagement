package ru.app.accountmanagement.service;

import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.app.accountmanagement.dto.UserDto;
import ru.app.accountmanagement.model.User;
import ru.app.accountmanagement.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void create(User user) {
        userRepository.save(user);
    }

    public void delete(UUID id) {
        userRepository.deleteById(id);
    }

    public User getById(UUID id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User update(User user, UserDto userDto) {
        user.setUsername(userDto.getUsername());
        return userRepository.save(user);
    }

}
