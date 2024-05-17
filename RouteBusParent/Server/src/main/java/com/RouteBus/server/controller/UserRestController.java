package com.RouteBus.server.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.RouteBus.server.model.User;
import com.RouteBus.server.service.UserService;
import com.RouteBus.server.service.UserService.UserServiceResult;

import java.util.Set;

@RestController
@RequestMapping("/user")
public class UserRestController {
    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all")
    public ResponseEntity<Set<User>> getUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
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
                return ResponseEntity.ok("{\"message\":\"User created successfully.\"}");
            case USER_ALREADY_EXISTS:
                return ResponseEntity.badRequest().body("{\"error\":\"User already exists.\"}");
            default:
                return ResponseEntity.internalServerError().body("{\"error\":\"Error creating user.\"}");
        }
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateUser(@RequestBody User user) {
        UserServiceResult result = userService.updateUser(user);
        switch (result) {
            case SUCCESS:
                return ResponseEntity.ok("{\"message\":\"User updated successfully.\"}");
            case USER_NOT_FOUND:
                return ResponseEntity.notFound().build();
            default:
                return ResponseEntity.internalServerError().body("{\"error\":\"Error updating user.\"}");
        }
    }

    @DeleteMapping("/delete/{email}")
    public ResponseEntity<String> deleteUser(@PathVariable String email) {
        UserServiceResult result = userService.deleteUser(email);
        return result == UserServiceResult.SUCCESS ? ResponseEntity.ok("{\"message\":\"User deleted successfully.\"}") : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/all")
    public ResponseEntity<String> deleteUsers() {
        userService.deleteAllUsers();
        return ResponseEntity.ok("{\"message\":\"All users deleted successfully.\"}");
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
