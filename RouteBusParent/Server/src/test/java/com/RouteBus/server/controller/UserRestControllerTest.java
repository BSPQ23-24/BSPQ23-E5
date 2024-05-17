package com.RouteBus.server.controller;

import com.RouteBus.server.model.User;
import com.RouteBus.server.service.UserService;
import com.RouteBus.server.service.UserService.UserServiceResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserRestControllerTest {

    private static final Logger logger = LoggerFactory.getLogger(UserRestControllerTest.class);

    @Mock
    private UserService userService;

    @InjectMocks
    private UserRestController userRestController;

    @Test
    public void testGetUsers() {
        Set<User> users = new HashSet<>();
        users.add(new User());
        users.add(new User());
        when(userService.getAllUsers()).thenReturn(users);

        ResponseEntity<Set<User>> response = userRestController.getUsers();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(users, response.getBody());
        verify(userService, times(1)).getAllUsers();
        logger.debug("Test testGetUsers passed successfully.");
    }

    @Test
    public void testGetUserByEmail() {
        User user = new User();
        when(userService.getUserByEmail("test@example.com")).thenReturn(user);

        ResponseEntity<User> response = userRestController.getUserByEmail("test@example.com");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
        verify(userService, times(1)).getUserByEmail("test@example.com");
        logger.debug("Test testGetUserByEmail passed successfully.");
    }

    @Test
    public void testGetUserByEmail_NotFound() {
        when(userService.getUserByEmail("nonexistent@example.com")).thenReturn(null);

        ResponseEntity<User> response = userRestController.getUserByEmail("nonexistent@example.com");
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(userService, times(1)).getUserByEmail("nonexistent@example.com");
        logger.debug("Test testGetUserByEmail_NotFound passed successfully.");
    }

    @Test
    public void testCreateUser_Success() {
        User user = new User();
        when(userService.createUser(user)).thenReturn(UserServiceResult.SUCCESS);

        ResponseEntity<String> response = userRestController.createUser(user);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("{\"message\":\"User created successfully.\"}", response.getBody());
        verify(userService, times(1)).createUser(user);
        logger.debug("Test testCreateUser_Success passed successfully.");
    }

    @Test
    public void testCreateUser_AlreadyExists() {
        User user = new User();
        when(userService.createUser(user)).thenReturn(UserServiceResult.USER_ALREADY_EXISTS);

        ResponseEntity<String> response = userRestController.createUser(user);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("{\"error\":\"User already exists.\"}", response.getBody());
        verify(userService, times(1)).createUser(user);
        logger.debug("Test testCreateUser_AlreadyExists passed successfully.");
    }

    @Test
    public void testCreateUser_InternalServerError() {
        User user = new User();
        when(userService.createUser(user)).thenReturn(UserServiceResult.ERROR);

        ResponseEntity<String> response = userRestController.createUser(user);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("{\"error\":\"Error creating user.\"}", response.getBody());
        verify(userService, times(1)).createUser(user);
        logger.debug("Test testCreateUser_InternalServerError passed successfully.");
    }

    @Test
    public void testUpdateUser_Success() {
        User user = new User();
        when(userService.updateUser(user)).thenReturn(UserServiceResult.SUCCESS);

        ResponseEntity<String> response = userRestController.updateUser(user);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("{\"message\":\"User updated successfully.\"}", response.getBody());
        verify(userService, times(1)).updateUser(user);
        logger.debug("Test testUpdateUser_Success passed successfully.");
    }

    @Test
    public void testUpdateUser_NotFound() {
        User user = new User();
        when(userService.updateUser(user)).thenReturn(UserServiceResult.USER_NOT_FOUND);

        ResponseEntity<String> response = userRestController.updateUser(user);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(userService, times(1)).updateUser(user);
        logger.debug("Test testUpdateUser_NotFound passed successfully.");
    }

    @Test
    public void testUpdateUser_InternalServerError() {
        User user = new User();
        when(userService.updateUser(user)).thenReturn(UserServiceResult.ERROR);

        ResponseEntity<String> response = userRestController.updateUser(user);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("{\"error\":\"Error updating user.\"}", response.getBody());
        verify(userService, times(1)).updateUser(user);
        logger.debug("Test testUpdateUser_InternalServerError passed successfully.");
    }

    @Test
    public void testDeleteUser_Success() {
        String email = "test@example.com";
        when(userService.deleteUser(email)).thenReturn(UserServiceResult.SUCCESS);

        ResponseEntity<String> response = userRestController.deleteUser(email);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("{\"message\":\"User deleted successfully.\"}", response.getBody());
        verify(userService, times(1)).deleteUser(email);
        logger.debug("Test testDeleteUser_Success passed successfully.");
    }

    @Test
    public void testDeleteUser_NotFound() {
        String email = "nonexistent@example.com";
        when(userService.deleteUser(email)).thenReturn(UserServiceResult.USER_NOT_FOUND);

        ResponseEntity<String> response = userRestController.deleteUser(email);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(userService, times(1)).deleteUser(email);
        logger.debug("Test testDeleteUser_NotFound passed successfully.");
    }

    @Test
    public void testDeleteUsers() {
        ResponseEntity<String> response = userRestController.deleteUsers();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("{\"message\":\"All users deleted successfully.\"}", response.getBody());
        verify(userService, times(1)).deleteAllUsers();
        logger.debug("Test testDeleteUsers passed successfully.");
    }

    @Test
    public void testCheckUser() {
        String email = "test@example.com";
        when(userService.checkUser(email)).thenReturn(true);

        ResponseEntity<Boolean> response = userRestController.checkUser(email);
        assertTrue(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(userService, times(1)).checkUser(email);
        logger.debug("Test testCheckUser passed successfully.");
    }

    @Test
    public void testCheckPassword() {
        String email = "test@example.com";
        String password = "password123";
        when(userService.checkPassword(email, password)).thenReturn(true);

        ResponseEntity<Boolean> response = userRestController.checkPassword(email, password);
        assertTrue(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(userService, times(1)).checkPassword(email, password);
        logger.debug("Test testCheckPassword passed successfully.");
    }
}
