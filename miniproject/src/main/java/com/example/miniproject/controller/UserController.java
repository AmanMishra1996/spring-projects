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
    private User getBooks(@PathVariable("userid") long user) {
        return userService.getUsersById(user);
    }


    @DeleteMapping("/user/{userid}")
    private void deleteBook(@PathVariable("userid") long userid) {
        userService.softDelete(userid);
    }


    @PostMapping("/users")
    private void saveBook(@RequestBody User user) {
        userService.addOrEditUser(user);
    }


    @PutMapping("/users")
    private User update(@RequestBody User user) {
        userService.addOrEditUser(user);
        return user;
    }


}
