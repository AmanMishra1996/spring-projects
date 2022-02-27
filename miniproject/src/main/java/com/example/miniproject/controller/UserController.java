package com.example.miniproject.controller;


import com.example.miniproject.model.User;
import com.example.miniproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    UserService userService;



    @GetMapping("/users/page={var}")
    private List<User> getAllUsers(@PathVariable("var") int initial) {
        System.out.println(initial);
        return userService.getAllUsers(initial);
    }


    @GetMapping("/users/{userid}")
    private User getUserById(@PathVariable("userid") long userId) {
        return userService.getUsersById(userId);
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
