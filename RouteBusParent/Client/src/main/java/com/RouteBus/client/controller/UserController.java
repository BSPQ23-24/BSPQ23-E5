package com.RouteBus.client.controller;

import com.RouteBus.client.gateway.UserGateway;
import com.RouteBus.client.dto.NationalityDTO;
import com.RouteBus.client.dto.UserDTO;
import java.util.List;

/**
 * Controller class for managing user-related operations.
 */
public class UserController {

    private static final UserController INSTANCE = new UserController();
    private final UserGateway userGateway;

    private UserController() {
        this.userGateway = UserGateway.getInstance();
    }

    /**
     * Retrieves the singleton instance of UserController.
     * @return The singleton instance of UserController.
     */
    public static UserController getInstance() {
        return INSTANCE;
    }

    /**
     * Retrieves all users.
     * @return A list of UserDTO objects representing all users.
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
     * Retrieves a user by email.
     * @param email The email of the user to retrieve.
     * @return The UserDTO object representing the user with the specified email, or null if not found.
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
     * Retrieves a list of all nationalities.
     * @return A list of NationalityDTO objects representing all nationalities.
     */
    public List<NationalityDTO> getNationalities() {
        return userGateway.getNationalities();
    }

}
