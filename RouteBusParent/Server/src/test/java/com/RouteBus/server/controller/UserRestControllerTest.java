//package com.RouteBus.server.controller;
//
//import org.apache.log4j.Logger;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.MockitoJUnitRunner;
//import org.springframework.http.ResponseEntity;
//
//import com.RouteBus.server.model.User;
//import com.RouteBus.server.service.UserService;
//import com.RouteBus.server.service.UserService.UserServiceResult;
//
//import java.util.Arrays;
//import java.util.List;
//
//import static org.mockito.Mockito.*;
//import static org.junit.Assert.*;
//
//@RunWith(MockitoJUnitRunner.class)
//public class UserRestControllerTest {
//
//    private static final Logger logger = Logger.getLogger(UserRestControllerTest.class);
//
//    @Mock
//    private UserService userService;
//
//    @InjectMocks
//    private UserRestController userRestController;
//
//    private User user;
//
//    @Before
//    public void setup() {
//        user = new User();
//        user.setId(1L);
//        user.setEmail("user@example.com");
//    }
//
//    @Test
//    public void getUsersTest() {
//        logger.debug("Starting test getUsersTest...");
//
//        when(userService.getAllUsers()).thenReturn(Arrays.asList(user));
//        List<User> result = userRestController.getUsers();
//        assertFalse(result.isEmpty());
//        assertEquals(1, result.size());
//        assertEquals(user.getEmail(), result.get(0).getEmail());
//
//        logger.debug("Test getUsersTest passed successfully.");
//    }
//
//    @Test
//    public void getUserByIdTest_UserExists() {
//        logger.debug("Starting test getUserByIdTest_UserExists...");
//
//        when(userService.getUserById(1L)).thenReturn(user);
//        ResponseEntity<User> response = userRestController.getUser(1L);
//        assertEquals(200, response.getStatusCodeValue());
//        assertNotNull(response.getBody());
//        assertEquals(user.getEmail(), response.getBody().getEmail());
//
//        logger.debug("Test getUserByIdTest_UserExists passed successfully.");
//    }
//
//    @Test
//    public void getUserByIdTest_UserDoesNotExist() {
//        logger.debug("Starting test getUserByIdTest_UserDoesNotExist...");
//
//        when(userService.getUserById(1L)).thenReturn(null);
//        ResponseEntity<User> response = userRestController.getUser(1L);
//        assertEquals(404, response.getStatusCodeValue());
//
//        logger.debug("Test getUserByIdTest_UserDoesNotExist passed successfully.");
//    }
//
//    @Test
//    public void createUserTest_Success() {
//        logger.debug("Starting test createUserTest_Success...");
//
//        when(userService.createUser(user)).thenReturn(UserServiceResult.SUCCESS);
//        ResponseEntity<String> response = userRestController.createUser(user);
//        assertEquals(200, response.getStatusCodeValue());
//        assertEquals("User created successfully.", response.getBody());
//
//        logger.debug("Test createUserTest_Success passed successfully.");
//    }
//
//    @Test
//    public void createUserTest_UserAlreadyExists() {
//        logger.debug("Starting test createUserTest_UserAlreadyExists...");
//
//        when(userService.createUser(user)).thenReturn(UserServiceResult.USER_ALREADY_EXISTS);
//        ResponseEntity<String> response = userRestController.createUser(user);
//        assertEquals(400, response.getStatusCodeValue());
//        assertEquals("User already exists.", response.getBody());
//
//        logger.debug("Test createUserTest_UserAlreadyExists passed successfully.");
//    }
//
//    @Test
//    public void updateUserTest_Success() {
//        logger.debug("Starting test updateUserTest_Success...");
//
//        when(userService.updateUser(user)).thenReturn(UserServiceResult.SUCCESS);
//        ResponseEntity<String> response = userRestController.updateUser(user);
//        assertEquals(200, response.getStatusCodeValue());
//        assertEquals("User updated successfully.", response.getBody());
//
//        logger.debug("Test updateUserTest_Success passed successfully.");
//    }
//
//    @Test
//    public void updateUserTest_UserNotFound() {
//        logger.debug("Starting test updateUserTest_UserNotFound...");
//
//        when(userService.updateUser(user)).thenReturn(UserServiceResult.USER_NOT_FOUND);
//        ResponseEntity<String> response = userRestController.updateUser(user);
//        assertEquals(404, response.getStatusCodeValue());
//
//        logger.debug("Test updateUserTest_UserNotFound passed successfully.");
//    }
//
//    @Test
//    public void deleteUserTest_Success() {
//        logger.debug("Starting test deleteUserTest_Success...");
//
//        when(userService.deleteUser("user@example.com")).thenReturn(UserServiceResult.SUCCESS);
//        ResponseEntity<String> response = userRestController.deleteUser("user@example.com");
//        assertEquals(200, response.getStatusCodeValue());
//        assertEquals("User deleted successfully.", response.getBody());
//
//        logger.debug("Test deleteUserTest_Success passed successfully.");
//    }
//
//    @Test
//    public void deleteUserTest_NotFound() {
//        logger.debug("Starting test deleteUserTest_NotFound...");
//
//        when(userService.deleteUser("user@example.com")).thenReturn(UserServiceResult.USER_NOT_FOUND);
//        ResponseEntity<String> response = userRestController.deleteUser("user@example.com");
//        assertEquals(404, response.getStatusCodeValue());
//
//        logger.debug("Test deleteUserTest_NotFound passed successfully.");
//    }
//
//    @Test
//    public void checkUserTest() {
//        logger.debug("Starting test checkUserTest...");
//
//        when(userService.checkUser("user@example.com")).thenReturn(true);
//        ResponseEntity<Boolean> response = userRestController.checkUser("user@example.com");
//        assertTrue(response.getBody());
//
//        logger.debug("Test checkUserTest passed successfully.");
//    }
//
//    @Test
//    public void checkPasswordTest() {
//        logger.debug("Starting test checkPasswordTest...");
//
//        when(userService.checkPassword("user@example.com", "password")).thenReturn(true);
//        ResponseEntity<Boolean> response = userRestController.checkPassword("user@example.com", "password");
//        assertTrue(response.getBody());
//
//        logger.debug("Test checkPasswordTest passed successfully.");
//    }
//
//    @Test
//    public void getUserByEmailTest_UserExists() {
//        logger.debug("Starting test getUserByEmailTest_UserExists...");
//
//        when(userService.getUserByEmail("user@example.com")).thenReturn(user);
//        ResponseEntity<User> response = userRestController.getUserByEmail("user@example.com");
//        assertEquals(200, response.getStatusCodeValue());
//        assertNotNull(response.getBody());
//        assertEquals("user@example.com", response.getBody().getEmail());
//
//        logger.debug("Test getUserByEmailTest_UserExists passed successfully.");
//    }
//
//    @Test
//    public void getUserByEmailTest_UserDoesNotExist() {
//        logger.debug("Starting test getUserByEmailTest_UserDoesNotExist...");
//
//        when(userService.getUserByEmail("user@example.com")).thenReturn(null);
//        ResponseEntity<User> response = userRestController.getUserByEmail("user@example.com");
//        assertEquals(404, response.getStatusCodeValue());
//
//        logger.debug("Test getUserByEmailTest_UserDoesNotExist passed successfully.");
//    }
//
//    @Test
//    public void createUserTest_ErrorCreatingUser() {
//        logger.debug("Starting test createUserTest_ErrorCreatingUser...");
//
//        when(userService.createUser(user)).thenReturn(UserServiceResult.ERROR);
//        ResponseEntity<String> response = userRestController.createUser(user);
//        assertEquals(500, response.getStatusCodeValue());
//        assertEquals("Error creating user.", response.getBody());
//
//        logger.debug("Test createUserTest_ErrorCreatingUser passed successfully.");
//    }
//
//    @Test
//    public void updateUserTest_ErrorUpdatingUser() {
//        logger.debug("Starting test updateUserTest_ErrorUpdatingUser...");
//
//        when(userService.updateUser(user)).thenReturn(UserServiceResult.ERROR);
//        ResponseEntity<String> response = userRestController.updateUser(user);
//        assertEquals(500, response.getStatusCodeValue());
//        assertEquals("Error updating user.", response.getBody());
//
//        logger.debug("Test updateUserTest_ErrorUpdatingUser passed successfully.");
//    }
//
//    @Test
//    public void deleteUserTest_DeleteAllUsers() {
//        logger.debug("Starting test deleteUserTest_DeleteAllUsers...");
//
//        ResponseEntity<String> response = userRestController.deleteUsers();
//
//        verify(userService).deleteAllUsers();
//        assertEquals(200, response.getStatusCodeValue());
//        assertEquals("All users deleted successfully.", response.getBody());
//
//        logger.debug("Test deleteUserTest_DeleteAllUsers passed successfully.");
//    }
//}
