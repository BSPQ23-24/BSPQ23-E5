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
        try {
            return userRepository.save(user) == null ? UserServiceResult.ERROR: UserServiceResult.SUCCESS;
        } catch (Exception e) {
            return UserServiceResult.ERROR;
        }
    }

    //Only updates non dependent fields
    public UserServiceResult updateUser(User user) {
        return userRepository.findByEmail(user.getEmail())
            .map(existingUser -> {
                existingUser.setFirstName(user.getFirstName());
                existingUser.setLastName(user.getLastName());
                existingUser.setEmail(user.getEmail());
                existingUser.setPassword(user.getPassword());
                existingUser.setNationality(user.getNationality());
                existingUser.setBirthDate(user.getBirthDate());
                existingUser.setRole(user.getRole());
                userRepository.save(existingUser);
                return UserServiceResult.SUCCESS;
            })
            .orElse(UserServiceResult.USER_NOT_FOUND);
    }



    public UserServiceResult deleteUser(String email) {
        return userRepository.findByEmail(email)
                .map(user -> {
                    userRepository.delete(user);
                    return UserServiceResult.SUCCESS;
                }).orElse(UserServiceResult.USER_NOT_FOUND);
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