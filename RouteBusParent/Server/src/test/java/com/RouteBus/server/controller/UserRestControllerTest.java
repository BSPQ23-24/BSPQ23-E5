package com.RouteBus.server.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import com.RouteBus.server.model.User;
import com.RouteBus.server.service.UserService;
import com.RouteBus.server.service.UserService.UserServiceResult;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class UserRestControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserRestController userRestController;

    private User user;
    
    @Before
    public void setup() {
        user = new User();
        user.setId(1L);
        user.setEmail("user@example.com");
    }

    @Test
    public void getUsersTest() {
        when(userService.getAllUsers()).thenReturn(Arrays.asList(user));
        List<User> result = userRestController.getUsers();
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(user.getEmail(), result.get(0).getEmail());
    }

    @Test
    public void getUserByIdTest_UserExists() {
        when(userService.getUserById(1L)).thenReturn(user);
        ResponseEntity<User> response = userRestController.getUser(1L);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(user.getEmail(), response.getBody().getEmail());
    }

    @Test
    public void getUserByIdTest_UserDoesNotExist() {
        when(userService.getUserById(1L)).thenReturn(null);
        ResponseEntity<User> response = userRestController.getUser(1L);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    public void createUserTest_Success() {
        when(userService.createUser(user)).thenReturn(UserServiceResult.SUCCESS);
        ResponseEntity<String> response = userRestController.createUser(user);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("User created successfully.", response.getBody());
    }

    @Test
    public void createUserTest_UserAlreadyExists() {
        when(userService.createUser(user)).thenReturn(UserServiceResult.USER_ALREADY_EXISTS);
        ResponseEntity<String> response = userRestController.createUser(user);
        assertEquals(400, response.getStatusCodeValue());
        assertEquals("User already exists.", response.getBody());
    }

    @Test
    public void updateUserTest_Success() {
        when(userService.updateUser(user)).thenReturn(UserServiceResult.SUCCESS);
        ResponseEntity<String> response = userRestController.updateUser(user);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("User updated successfully.", response.getBody());
    }

    @Test
    public void updateUserTest_UserNotFound() {
        when(userService.updateUser(user)).thenReturn(UserServiceResult.USER_NOT_FOUND);
        ResponseEntity<String> response = userRestController.updateUser(user);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    public void deleteUserTest_Success() {
        when(userService.deleteUser("user@example.com")).thenReturn(UserServiceResult.SUCCESS);
        ResponseEntity<String> response = userRestController.deleteUser("user@example.com");
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("User deleted successfully.", response.getBody());
    }

    @Test
    public void deleteUserTest_NotFound() {
        when(userService.deleteUser("user@example.com")).thenReturn(UserServiceResult.USER_NOT_FOUND);
        ResponseEntity<String> response = userRestController.deleteUser("user@example.com");
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    public void checkUserTest() {
        when(userService.checkUser("user@example.com")).thenReturn(true);
        ResponseEntity<Boolean> response = userRestController.checkUser("user@example.com");
        assertTrue(response.getBody());
    }

    @Test
    public void checkPasswordTest() {
        when(userService.checkPassword("user@example.com", "password")).thenReturn(true);
        ResponseEntity<Boolean> response = userRestController.checkPassword("user@example.com", "password");
        assertTrue(response.getBody());
    }
    
    @Test
    public void getUserByEmailTest_UserExists() {
        when(userService.getUserByEmail("user@example.com")).thenReturn(user);
        ResponseEntity<User> response = userRestController.getUserByEmail("user@example.com");
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("user@example.com", response.getBody().getEmail());
    }

    @Test
    public void getUserByEmailTest_UserDoesNotExist() {
        when(userService.getUserByEmail("user@example.com")).thenReturn(null);
        ResponseEntity<User> response = userRestController.getUserByEmail("user@example.com");
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    public void createUserTest_ErrorCreatingUser() {
        when(userService.createUser(user)).thenReturn(UserServiceResult.ERROR);
        ResponseEntity<String> response = userRestController.createUser(user);
        assertEquals(500, response.getStatusCodeValue());
        assertEquals("Error creating user.", response.getBody());
    }

    @Test
    public void updateUserTest_ErrorUpdatingUser() {
        when(userService.updateUser(user)).thenReturn(UserServiceResult.ERROR);
        ResponseEntity<String> response = userRestController.updateUser(user);
        assertEquals(500, response.getStatusCodeValue());
        assertEquals("Error updating user.", response.getBody());
    }

    @Test
    public void deleteUserTest_DeleteAllUsers() {
        ResponseEntity<String> response = userRestController.deleteUsers();

        verify(userService).deleteAllUsers(); 
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("All users deleted successfully.", response.getBody());
    }

}
