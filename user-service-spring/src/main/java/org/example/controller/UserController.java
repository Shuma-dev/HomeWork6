package org.example.controller;

import org.example.dto.UserRequestDto;
import org.example.dto.UserResponseDto;
import org.example.entity.User;
import org.example.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    private UserResponseDto toDto(User user) {
        return new UserResponseDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getAge(),
                user.getCreatedAt());
    }

    @GetMapping
    public List<UserResponseDto> getUsers() {
        List<User> users = userService.findAll();
        List<UserResponseDto> response = new ArrayList<>();
        for (User user : users) {
            response.add(toDto(user));
        }
        return response;
    }

    @GetMapping("/{id}")
    public UserResponseDto getUser(@PathVariable Long id) {
        return toDto(userService.findById(id));
    }

    @PostMapping
    public void createUser(@RequestBody UserRequestDto request) {
        userService.createUser(request.getName(), request.getEmail(), request.getAge());
    }

    @PutMapping("/{id}")
    public void updateUser(@PathVariable Long id, @RequestBody UserRequestDto request) {
        userService.updateUser(id, request.getName(), request.getEmail(), request.getAge());
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

}
