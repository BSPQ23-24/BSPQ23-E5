//package com.RouteBus.server.service;
//
//import static org.junit.Assert.*;
//import static org.mockito.Mockito.*;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import org.junit.Before;
//import org.junit.Test;
//
//import com.RouteBus.server.dao.UserRepository;
//import com.RouteBus.server.model.User;
//
//public class UserServiceTest {
//
//    private UserService userService;
//    private UserRepository userRepository;
//
//    @Before
//    public void setUp() {
//        userRepository = mock(UserRepository.class);
//        userService = new UserService(userRepository);
//    }
//
//    @Test
//    public void testGetUserById() {
//        User user = new User();
//        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
//
//        assertEquals(user, userService.getUserById(user.getId()));
//    }
//
//    @Test
//    public void testGetUserByEmail() {
//        String email = "test@example.com";
//        User user = new User();
//        user.setEmail(email);
//        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
//
//        assertEquals(user, userService.getUserByEmail(email));
//    }
//
//    @Test
//    public void testGetAllUsers() {
//        List<User> userList = new ArrayList<>();
//        userList.add(new User());
//        userList.add(new User());
//        when(userRepository.findAll()).thenReturn(userList);
//
//        assertEquals(userList, userService.getAllUsers());
//    }
//
//    @Test
//    public void testCreateUser_Success() {
//        User user = new User();
//        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());
//        when(userRepository.save(user)).thenReturn(user);
//
//        assertEquals(UserService.UserServiceResult.SUCCESS, userService.createUser(user));
//    }
//
//}
