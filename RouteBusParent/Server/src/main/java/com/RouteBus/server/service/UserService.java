package com.RouteBus.server.service;


import org.springframework.stereotype.Service;

import com.RouteBus.server.dao.UserRepository;
import com.RouteBus.server.model.User;

import java.util.List;
import java.util.Optional;

//Use @Service annotation for BUSINESS LOGIC and access to the @REPOSITORY
@Service
public class UserService {

    private UserRepository userRepository;
    public enum UserServiceResult {
		SUCCESS,
		FAIL;
	}
        
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    /**  Returning User information */
    public User getUserById(Long id) {
    	Optional<User> result = userRepository.findById(id);
    	return result.orElse(null);
    }
    
    public User getUserByEmail(String email) {
    	Optional<User> result = userRepository.findByEmail(email);
    	return result.orElse(null);
    }
    
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /** Creating a New User */
	public UserServiceResult createUser(User user) {
		Optional<User> result = userRepository.findByEmail(user.getEmail());
		
		if (result.isEmpty()) {
			User savedUser = userRepository.save(user);
			
			if (savedUser != null) {
				return UserServiceResult.SUCCESS;
			}
		}
		
		return UserServiceResult.FAIL;
	}    
   
    /** Update an Existing User */
    public UserServiceResult updateUser(User user, Long id) {
    	Optional<User> result = userRepository.findById(id);
		
		if (!result.isEmpty()) {
			User updatedUser = result.get();
			
			updatedUser.setFirstName(user.getFirstName());
			updatedUser.setLastName(user.getLastName());
			updatedUser.setEmail(user.getEmail());

			userRepository.save(updatedUser);
			
			if (!userRepository.findById(id).isEmpty()) {
				return UserServiceResult.SUCCESS;
			}
		}
		
		return UserServiceResult.FAIL;
    }
  
    /** Delete one User*/
    public UserServiceResult deleteUser(Long id) {
    	Optional<User> result = userRepository.findById(id);
		
		if (!result.isEmpty()) {
			userRepository.delete(result.get());

			if (userRepository.findById(id).isEmpty()) {
				return UserServiceResult.SUCCESS;
			}
		}
		
		return UserServiceResult.FAIL;
    }
    
    /** Delete all Users in the database  */
    public UserServiceResult deleteAllUsers() {
    	UserServiceResult result = UserServiceResult.SUCCESS;
		
		for (User u : userRepository.findAll()) {
			userRepository.deleteById(u.getId());

			if (!userRepository.findById(u.getId()).isEmpty()) {
				result = UserServiceResult.FAIL;
			}
		}
		return result;
    }
    
    /** Checks if a User exists in the database  */
    public boolean checkUser(String email) {
        return userRepository.findByEmail(email).isPresent();
    }
    
    /** Checks the password of a given user in the database  */
    public boolean checkPassword(String email, String password) {
    	return userRepository.findByEmail(email)
            .map(User::getPassword)
            .filter(storedPassword -> storedPassword.equals(password))
            .isPresent();
    }
}