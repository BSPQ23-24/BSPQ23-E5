package com.RouteBus.client.controller;

import com.RouteBus.client.gateway.UserGateway;
import com.RouteBus.client.dto.NationalityDTO;
import com.RouteBus.client.dto.UserDTO;
import java.util.List;

/**
 * UserController handles user-related operations such as fetching, creating, updating, and deleting users.
 */
public class UserController {
    private static final UserController INSTANCE = new UserController();
    private final UserGateway userGateway;

    private UserController() {
        this.userGateway = UserGateway.getInstance();
    }

    /**
     * Returns the singleton instance of UserController.
     *
     * @return the singleton instance of UserController
     */
    public static UserController getInstance() {
        return INSTANCE;
    }

    /**
     * Retrieves all users.
     *
     * @return a list of UserDTO representing all users, or null if an error occurs
     */
    public List<UserDTO> getAllUsers() {
        try {
            return userGateway.getAllUsers();
        } catch (Exception e) {
            System.err.println("Failed to fetch users: " + e.getMessage());
            return null;
        }
    }

    /**
     * Retrieves a user by their email.
     *
     * @param email the email of the user to retrieve
     * @return the UserDTO representing the user, or null if an error occurs
     */
    public UserDTO getUserByEmail(String email) {
        try {
            return userGateway.getUserByEmail(email);
        } catch (Exception e) {
            System.err.println("Failed to fetch user: " + e.getMessage());
            return null;
        }
    }

    /**
     * Creates a new user.
     *
     * @param user the UserDTO representing the user to create
     * @return true if the user is successfully created, false otherwise
     */
    public boolean createUser(UserDTO user) {
        try {
            return userGateway.createUser(user);
        } catch (Exception e) {
            System.err.println("Failed to create user: " + e.getMessage());
            return false;
        }
    }

    /**
     * Updates an existing user.
     *
     * @param user the UserDTO representing the user to update
     * @return true if the user is successfully updated, false otherwise
     */
    public boolean updateUser(UserDTO user) {
        try {
            return userGateway.updateUser(user);
        } catch (Exception e) {
            System.err.println("Failed to update user: " + e.getMessage());
            return false;
        }
    }

    /**
     * Deletes a user by their email.
     *
     * @param email the email of the user to delete
     * @return true if the user is successfully deleted, false otherwise
     */
    public boolean deleteUser(String email) {
        try {
            return userGateway.deleteUser(email);
        } catch (Exception e) {
            System.err.println("Failed to delete user: " + e.getMessage());
            return false;
        }
    }

    /**
     * Checks if a user exists by their email.
     *
     * @param email the email of the user to check
     * @return true if the user exists, false otherwise
     */
    public boolean checkUser(String email) {
        try {
            return userGateway.checkUser(email);
        } catch (Exception e) {
            System.err.println("Failed to check user existence: " + e.getMessage());
            return false;
        }
    }

    /**
     * Verifies a user's password.
     *
     * @param email the email of the user
     * @param password the password to verify
     * @return true if the password is correct, false otherwise
     */
    public boolean checkPassword(String email, String password) {
        try {
            return userGateway.checkPassword(email, password);
        } catch (Exception e) {
            System.err.println("Failed to verify password: " + e.getMessage());
            return false;
        }
    }

    /**
     * Retrieves a list of nationalities.
     *
     * @return a list of NationalityDTO representing the nationalities
     */
    public List<NationalityDTO> getNationalities() {
        return userGateway.getNationalities();
    }
}
