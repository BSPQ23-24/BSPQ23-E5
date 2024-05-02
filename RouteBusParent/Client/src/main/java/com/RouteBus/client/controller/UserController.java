package com.RouteBus.client.controller;

import com.RouteBus.client.gateway.UserGateway;
import com.RouteBus.client.dto.UserDTO;
import java.util.List;

public class UserController {
    private static final UserController INSTANCE = new UserController();
    private final UserGateway userGateway;

    private UserController() {
        this.userGateway = UserGateway.getInstance();
    }

    public static UserController getInstance() {
        return INSTANCE;
    }

    public List<UserDTO> getAllUsers() {
        try {
            return userGateway.getAllUsers();
        } catch (Exception e) {
            System.err.println("Failed to fetch users: " + e.getMessage());
            return null;
        }
    }

    public UserDTO getUserByEmail(String email) {
        try {
            return userGateway.getUserByEmail(email);
        } catch (Exception e) {
            System.err.println("Failed to fetch user: " + e.getMessage());
            return null;
        }
    }

    public boolean createUser(UserDTO user) {
        try {
            return userGateway.createUser(user);
        } catch (Exception e) {
            System.err.println("Failed to create user: " + e.getMessage());
            return false;
        }
    }

    public boolean updateUser(String email, UserDTO user) {
        try {
            return userGateway.updateUser(email, user);
        } catch (Exception e) {
            System.err.println("Failed to update user: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteUser(String email) {
        try {
            return userGateway.deleteUser(email);
        } catch (Exception e) {
            System.err.println("Failed to delete user: " + e.getMessage());
            return false;
        }
    }

    public boolean checkUser(String email) {
        try {
            return userGateway.checkUser(email);
        } catch (Exception e) {
            System.err.println("Failed to check user existence: " + e.getMessage());
            return false;
        }
    }

    public boolean checkPassword(String email, String password) {
        try {
            return userGateway.checkPassword(email, password);
        } catch (Exception e) {
            System.err.println("Failed to verify password: " + e.getMessage());
            return false;
        }
    }
    
    public List<String> getNationalities() {
        return userGateway.getNationalities();
    }

}
