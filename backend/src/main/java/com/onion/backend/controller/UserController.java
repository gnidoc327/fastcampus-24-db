package com.onion.backend.controller;

import com.onion.backend.entity.User;
import com.onion.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestParam String username, @RequestParam String password, @RequestParam String email) {
        User user = userService.createUser(username, password, email);
        return ResponseEntity.ok(user);
    }
}
