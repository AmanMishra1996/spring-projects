package com.example.miniproject.controller;


import com.example.miniproject.dto.UserDto;
import com.example.miniproject.model.User;
import com.example.miniproject.modelmapper.UserDtoToAndFromUser;
import com.example.miniproject.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    private UserDto getUserById(@PathVariable("userid") long userId) {
        return userDtoToAndFromUser.toUserDto(userService.getUsersById(userId));
    }


    @DeleteMapping("/user/{userid}")
    private void deleteUserByUserId(@PathVariable("userid") long userId) {
        userService.softDelete(userId);
    }


    @PostMapping("/users")
    private void addUser(@RequestBody User user,@RequestHeader("X-CSCAPI-KEY") String xCscApiKeyValue) {
        userService.addOrEditUser(user, xCscApiKeyValue);
    }


    @PutMapping("/users")
    private User editUser(@RequestBody User user,@RequestHeader("X-CSCAPI-KEY") String xCscApiKeyValue) {
        userService.addOrEditUser(user, xCscApiKeyValue);
        return user;
    }
}
