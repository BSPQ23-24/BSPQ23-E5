package com.RouteBus.server.service;

import org.springframework.stereotype.Service;
import com.RouteBus.server.dao.UserRepository;
import com.RouteBus.server.model.User;

import java.util.List;

@Service
public class UserService {

    public enum UserServiceResult {
        SUCCESS,
        USER_ALREADY_EXISTS,
        USER_NOT_FOUND,
        ERROR
    }

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public UserServiceResult createUser(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return UserServiceResult.USER_ALREADY_EXISTS;
        }
        return userRepository.save(user) != null ? UserServiceResult.SUCCESS : UserServiceResult.ERROR;
    }

    public UserServiceResult updateUser(User user, Long id) {
        return userRepository.findById(id)
            .map(existingUser -> {
                existingUser.setFirstName(user.getFirstName());
                existingUser.setLastName(user.getLastName());
                existingUser.setEmail(user.getEmail());
                userRepository.save(existingUser);
                return UserServiceResult.SUCCESS;
            }).orElse(UserServiceResult.USER_NOT_FOUND);
    }

    public UserServiceResult deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return UserServiceResult.SUCCESS;
        }
        return UserServiceResult.USER_NOT_FOUND;
    }

    public UserServiceResult deleteAllUsers() {
        userRepository.deleteAll();
        return UserServiceResult.SUCCESS;
    }

    public boolean checkUser(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public boolean checkPassword(String email, String password) {
        return userRepository.findByEmail(email)
                .map(User::getPassword)
                .filter(pwd -> pwd.equals(password))
                .isPresent();
    }
}