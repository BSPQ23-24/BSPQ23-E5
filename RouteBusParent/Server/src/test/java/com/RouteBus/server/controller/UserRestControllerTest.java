package com.RouteBus.server.controller;

import com.RouteBus.server.controller.UserRestController;
import com.RouteBus.server.model.User;
import com.RouteBus.server.service.UserService;
import com.RouteBus.server.service.UserService.UserServiceResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserRestControllerTest {

    private static final Logger logger = LoggerFactory.getLogger(UserRestControllerTest.class);

    @Mock
    private UserService userService;

    @InjectMocks
    private UserRestController userRestController;

    @Test
    public void testGetUsers() {
        logger.info("Testing getUsers method");
        List<User> users = Arrays.asList(new User(), new User());
        when(userService.getAllUsers()).thenReturn(users);

        assertEquals(users, userRestController.getUsers());
    }

    @Test
    public void testGetUserByEmail() {
        logger.info("Testing getUserByEmail method");
        User user = new User();
        when(userService.getUserByEmail("test@example.com")).thenReturn(user);

        ResponseEntity<User> response = userRestController.getUserByEmail("test@example.com");
        assertEquals(user, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testGetUserByEmail_NotFound() {
        logger.info("Testing getUserByEmail method for not found case");
        when(userService.getUserByEmail("nonexistent@example.com")).thenReturn(null);

        ResponseEntity<User> response = userRestController.getUserByEmail("nonexistent@example.com");
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testCreateUser_Success() {
        logger.info("Testing createUser method for success case");
        User user = new User();
        when(userService.createUser(user)).thenReturn(UserServiceResult.SUCCESS);

        ResponseEntity<String> response = userRestController.createUser(user);
        assertEquals("User created successfully.", response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testCreateUser_AlreadyExists() {
        logger.info("Testing createUser method for already exists case");
        User user = new User();
        when(userService.createUser(user)).thenReturn(UserServiceResult.USER_ALREADY_EXISTS);

        ResponseEntity<String> response = userRestController.createUser(user);
        assertEquals("User already exists.", response.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testCreateUser_InternalServerError() {
        logger.info("Testing createUser method for internal server error case");
        User user = new User();
        when(userService.createUser(user)).thenReturn(UserServiceResult.ERROR);

        ResponseEntity<String> response = userRestController.createUser(user);
        assertEquals("Error creating user.", response.getBody());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testUpdateUser_Success() {
        logger.info("Testing updateUser method for success case");
        User user = new User();
        when(userService.updateUser(user)).thenReturn(UserServiceResult.SUCCESS);

        ResponseEntity<String> response = userRestController.updateUser(user);
        assertEquals("User updated successfully.", response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testUpdateUser_NotFound() {
        logger.info("Testing updateUser method for not found case");
        User user = new User();
        when(userService.updateUser(user)).thenReturn(UserServiceResult.USER_NOT_FOUND);

        ResponseEntity<String> response = userRestController.updateUser(user);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testUpdateUser_InternalServerError() {
        logger.info("Testing updateUser method for internal server error case");
        User user = new User();
        when(userService.updateUser(user)).thenReturn(UserServiceResult.ERROR);

        ResponseEntity<String> response = userRestController.updateUser(user);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testDeleteUser_Success() {
        logger.info("Testing deleteUser method for success case");
        String email = "test@example.com";
        when(userService.deleteUser(email)).thenReturn(UserServiceResult.SUCCESS);

        ResponseEntity<String> response = userRestController.deleteUser(email);
        assertEquals("User deleted successfully.", response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testDeleteUser_NotFound() {
        logger.info("Testing deleteUser method for not found case");
        String email = "nonexistent@example.com";
        when(userService.deleteUser(email)).thenReturn(UserServiceResult.USER_NOT_FOUND);

        ResponseEntity<String> response = userRestController.deleteUser(email);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testDeleteUsers() {
        logger.info("Testing deleteUsers method");
        ResponseEntity<String> response = userRestController.deleteUsers();
        assertEquals("All users deleted successfully.", response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(userService, times(1)).deleteAllUsers();
    }

    @Test
    public void testCheckUser() {
        logger.info("Testing checkUser method");
        String email = "test@example.com";
        when(userService.checkUser(email)).thenReturn(true);

        ResponseEntity<Boolean> response = userRestController.checkUser(email);
        assertTrue(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testCheckPassword() {
        logger.info("Testing checkPassword method");
        String email = "test@example.com";
        String password = "password123";
        when(userService.checkPassword(email, password)).thenReturn(true);

        ResponseEntity<Boolean> response = userRestController.checkPassword(email, password);
        assertTrue(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
