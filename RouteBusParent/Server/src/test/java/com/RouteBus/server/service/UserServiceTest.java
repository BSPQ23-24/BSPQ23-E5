package com.RouteBus.server.service;

import com.RouteBus.server.dao.UserRepository;
import com.RouteBus.server.model.Nationality;
import com.RouteBus.server.model.Ticket;
import com.RouteBus.server.model.User;
import com.RouteBus.server.model.UserRole;
import com.RouteBus.server.service.UserService.UserServiceResult;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private final Logger logger = Logger.getLogger(UserServiceTest.class);

    @Before
    public void setUp() {
    }

    @Test
    public void testGetUserByEmail() {
        String email = "test@example.com";
        User user = new User();
        user.setEmail(email);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        User result = userService.getUserByEmail(email);
        assertEquals(user, result);

        logger.info("getUserByEmail test passed.");
    }

    @Test
    public void testGetAllUsers() {
        List<User> userList = List.of(new User(), new User());
        when(userRepository.findAll()).thenReturn(userList);

        List<User> result = userService.getAllUsers();
        assertEquals(userList, result);

        logger.info("getAllUsers test passed.");
    }

    @Test
    public void testCreateUser_UserDoesNotExist() {
        User newUser = new User();
        newUser.setEmail("test@example.com");

        when(userRepository.findByEmail(newUser.getEmail())).thenReturn(Optional.empty());
        when(userRepository.save(newUser)).thenReturn(newUser);

        UserService.UserServiceResult result = userService.createUser(newUser);
        assertEquals(UserService.UserServiceResult.SUCCESS, result);

        logger.info("createUser_UserDoesNotExist test passed.");
    }

    @Test
    public void testCreateUser_UserAlreadyExists() {
        User existingUser = new User();
        existingUser.setEmail("test@example.com");

        when(userRepository.findByEmail(existingUser.getEmail())).thenReturn(Optional.of(existingUser));

        UserService.UserServiceResult result = userService.createUser(existingUser);
        assertEquals(UserService.UserServiceResult.USER_ALREADY_EXISTS, result);

        logger.info("createUser_UserAlreadyExists test passed.");
    }
    
    @Test
    public void testCreateUser_SaveError() {
        User user = new User();
        user.setEmail("test@example.com");

        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());
        when(userRepository.save(user)).thenThrow(new RuntimeException("Error saving user"));

        UserService.UserServiceResult result = userService.createUser(user);

        assertEquals(UserService.UserServiceResult.ERROR, result);

        logger.info("createUser_SaveError test passed.");
    }
    
    @Test
    public void testCreateUser_SaveSuccess() {
        User user = new User();
        user.setEmail("test@example.com");

        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());
        when(userRepository.save(user)).thenReturn(user);

        UserService.UserServiceResult result = userService.createUser(user);

        assertEquals(UserService.UserServiceResult.SUCCESS, result);

        logger.info("createUser_SaveSuccess test passed.");
    }

    @Test
    public void testCreateUser_SaveErrorException() {
        User user = new User();
        user.setEmail("test@example.com");

        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());
        when(userRepository.save(user)).thenReturn(null);

        UserService.UserServiceResult result = userService.createUser(user);

        assertEquals(UserService.UserServiceResult.ERROR, result);

        logger.info("createUser_SaveError test passed.");
    }


    @Test
    public void testUpdateUser_UserFound() {
        User existingUser = new User("John", "Doe", "test@example.com" ,"password", null, new Date());
        User updatedUser = new User("Diego", "Merino", "test@example.com", "newpassword", new Nationality(), new Date(1l), UserRole.ADMIN, new HashSet<Ticket>());
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(existingUser));
        UserServiceResult result = userService.updateUser(updatedUser);
        assertEquals(UserServiceResult.SUCCESS, result);
        assertEquals("newpassword", existingUser.getPassword());
        assertEquals(UserRole.ADMIN, existingUser.getRole());
        verify(userRepository, times(1)).findByEmail("test@example.com");
        verify(userRepository, times(1)).save(existingUser);
    }
    
    @Test
    public void testUpdateUser_NoChange() {
    	User existingUser = new User("John", "Doe", "test@example.com" ,"password", null, new Date());
    	User updatedUser = new User("John", "Doe", "test@example.com" ,"password", null, new Date());
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(existingUser));
        UserServiceResult result = userService.updateUser(updatedUser);
        assertEquals(UserServiceResult.SUCCESS, result);
        verify(userRepository, times(1)).findByEmail("test@example.com");
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    public void testUpdateUser_UserNotFound() {
        User updatedUser = new User();
        updatedUser.setEmail("test@example.com");

        when(userRepository.findByEmail(updatedUser.getEmail())).thenReturn(Optional.empty());

        UserService.UserServiceResult result = userService.updateUser(updatedUser);
        assertEquals(UserService.UserServiceResult.USER_NOT_FOUND, result);

        logger.info("updateUser_UserNotFound test passed.");
    }

    @Test
    public void testDeleteUser_UserFound() {
        String email = "test@example.com";
        User userToDelete = new User();
        userToDelete.setEmail(email);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(userToDelete));

        UserService.UserServiceResult result = userService.deleteUser(email);
        assertEquals(UserService.UserServiceResult.SUCCESS, result);

        logger.info("deleteUser_UserFound test passed.");
    }

    @Test
    public void testDeleteUser_UserNotFound() {
        String email = "test@example.com";

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        UserService.UserServiceResult result = userService.deleteUser(email);
        assertEquals(UserService.UserServiceResult.USER_NOT_FOUND, result);

        logger.info("deleteUser_UserNotFound test passed.");
    }

    @Test
    public void testDeleteAllUsers() {
        UserService.UserServiceResult result = userService.deleteAllUsers();
        assertEquals(UserService.UserServiceResult.SUCCESS, result);

        logger.info("deleteAllUsers test passed.");
    }

    @Test
    public void testCheckUser_UserExists() {
        String email = "test@example.com";

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(new User()));

        boolean result = userService.checkUser(email);
        assertTrue(result);

        logger.info("checkUser_UserExists test passed.");
    }

    @Test
    public void testCheckUser_UserDoesNotExist() {
        String email = "test@example.com";

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        boolean result = userService.checkUser(email);
        assertFalse(result);

        logger.info("checkUser_UserDoesNotExist test passed.");
    }

    @Test
    public void testCheckPassword_CorrectPassword() {
        String email = "test@example.com";
        String password = "password";

        User user = new User();
        user.setEmail(email);
        user.setPassword(password);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        boolean result = userService.checkPassword(email, password);
        assertTrue(result);

        logger.info("checkPassword_CorrectPassword test passed.");
    }

    @Test
    public void testCheckPassword_IncorrectPassword() {
        String email = "test@example.com";
        String password = "password";

        User user = new User();
        user.setEmail(email);
        user.setPassword("wrongpassword");

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        boolean result = userService.checkPassword(email, password);
        assertFalse(result);

        logger.info("checkPassword_IncorrectPassword test passed.");
    }
}
