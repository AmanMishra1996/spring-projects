package com.example.miniproject.controller;


import com.example.miniproject.dto.UserDto;
import com.example.miniproject.model.User;
import com.example.miniproject.modelmapper.UserDtoToAndFromUser;
import com.example.miniproject.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService userService;
    private final UserDtoToAndFromUser userDtoToAndFromUser;

    public UserController(UserService userService, UserDtoToAndFromUser userDtoToAndFromUser) {
        this.userService = userService;
        this.userDtoToAndFromUser = userDtoToAndFromUser;
    }

    @GetMapping("/users/page={var}")
    private List<UserDto> getAllUsers(@PathVariable("var") int initial) {
        return userService.getAllUsers(initial)
                .stream()
                .map(userDtoToAndFromUser::toUserDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/users/{userid}")
    private UserDto getUserById(@PathVariable("userid") long userDtoId) {
        Optional<User> usersById = userService.getUsersById(userDtoId);
        return usersById.map(userDtoToAndFromUser::toUserDto).orElse(null);
    }

    @DeleteMapping("/user/{userid}")
    private void deleteUserByUserId(@PathVariable("userid") long userId) {
        userService.softDelete(userId);
    }

    @PostMapping("/users")
    private void addUser(@RequestBody UserDto user,@RequestHeader("X-CSCAPI-KEY") String xCscApiKeyValue) {
        // Deserialization to User working fine ,with dateOfBirth as LocalDate
        System.out.println(user);
        userService.addOrEditUser(userDtoToAndFromUser.toUser(user), xCscApiKeyValue);
    }

    @PutMapping("/users")
    private void editUser(@RequestBody UserDto user, @RequestHeader("X-CSCAPI-KEY") String xCscApiKeyValue) {
        // Deserialisation from JSON to UserDto isn't working when dateOfBirth is LocalDate Type
        // Deserialisation from JSON to UserDto is working when dateOfBirth is String Type

        System.out.println(user);
        userService.addOrEditUser(userDtoToAndFromUser.toUser(user), xCscApiKeyValue);
    }
}
