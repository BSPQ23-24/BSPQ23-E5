package com.RouteBus.server.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.RouteBus.server.model.User;
import com.RouteBus.server.service.UserService;
import com.RouteBus.server.service.UserService.UserServiceResult;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserRestController {
    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all")
    public List<User> getUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/details/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        User user = userService.getUserByEmail(email);
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }

    @PostMapping("/create")
    public ResponseEntity<String> createUser(@RequestBody User user) {
        UserServiceResult result = userService.createUser(user);
        switch (result) {
            case SUCCESS:
                return ResponseEntity.ok("User created successfully.");
            case USER_ALREADY_EXISTS:
                return ResponseEntity.badRequest().body("User already exists.");
            default:
                return ResponseEntity.internalServerError().body("Error creating user.");
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody User user) {
        UserServiceResult result = userService.updateUser(user, id);
        switch (result) {
            case SUCCESS:
                return ResponseEntity.ok("User updated successfully.");
            case USER_NOT_FOUND:
                return ResponseEntity.notFound().build();
            default:
                return ResponseEntity.internalServerError().body("Error updating user.");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        UserServiceResult result = userService.deleteUser(id);
        return result == UserServiceResult.SUCCESS ? ResponseEntity.ok("User deleted successfully.") : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/all")
    public ResponseEntity<String> deleteUsers() {
        userService.deleteAllUsers();
        return ResponseEntity.ok("All users deleted successfully.");
    }

    @GetMapping("/check/{email}")
    public ResponseEntity<Boolean> checkUser(@PathVariable String email) {
        return ResponseEntity.ok(userService.checkUser(email));
    }

    @PostMapping("/check-password")
    public ResponseEntity<Boolean> checkPassword(@RequestParam String email, @RequestParam String password) {
        return ResponseEntity.ok(userService.checkPassword(email, password));
    }
}