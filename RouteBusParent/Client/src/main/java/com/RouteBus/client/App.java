package com.RouteBus.client;

import com.RouteBus.client.controller.UserController;
import com.RouteBus.client.dto.UserDTO;
import com.RouteBus.client.gui.LoginWindow;
import com.RouteBus.client.dto.NationalityDTO;

import java.util.Date;
import java.util.List;

public class App {
    public static void main(String[] args) {
//        testServerConnectivity();
    	new LoginWindow();
    }

    public static void testServerConnectivity() {
        UserController controller = UserController.getInstance();

        NationalityDTO nationality = new NationalityDTO("American");
        UserDTO newUser = new UserDTO("Test", "User", "test@example.com", "password123", nationality, new Date(), UserDTO.UserRole.CUSTOMER);

        System.out.println("Testing user creation...");
        boolean createUserResult = controller.createUser(newUser);
        System.out.println("Create User Result: " + createUserResult);

        System.out.println("Testing getting all users...");
        List<UserDTO> users = controller.getAllUsers();
        if (users != null) {
            System.out.println("Number of users fetched: " + users.size());
        } else {
            System.out.println("Failed to fetch users");
        }

        System.out.println("Testing getting user by email...");
        UserDTO userByEmail = controller.getUserByEmail("test@example.com");
        System.out.println("Get User by Email Result: " + (userByEmail != null ? "Success" : "Failed"));

        System.out.println("Testing updating a user...");
        if (userByEmail != null) {
            userByEmail.setFirstName("Updated Test");
            boolean updateUserResult = controller.updateUser(userByEmail);
            System.out.println("Update User Result: " + updateUserResult);
        }

        System.out.println("Testing deleting a user...");
        boolean deleteUserResult = controller.deleteUser("test@example.com");
        System.out.println("Delete User Result: " + deleteUserResult);

        System.out.println("Testing user check...");
        boolean checkUserResult = controller.checkUser("test@example.com");
        System.out.println("Check User Result: " + checkUserResult);

        System.out.println("Testing password check...");
        boolean checkPasswordResult = controller.checkPassword("test@example.com", "password123");
        System.out.println("Check Password Result: " + checkPasswordResult);

        System.out.println("Testing getting nationalities...");
        List<NationalityDTO> nationalities = controller.getNationalities();
        if (nationalities != null) {
            System.out.println("Number of nationalities fetched: " + nationalities.size());
        } else {
            System.out.println("Failed to fetch nationalities");
        }
    }
}