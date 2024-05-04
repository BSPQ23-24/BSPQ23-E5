package com.RouteBus.server.service;

import org.springframework.stereotype.Service;
import com.RouteBus.server.dao.UserRepository;
import com.RouteBus.server.model.User;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;

@Service
public class UserService {

	public enum UserServiceResult {
		SUCCESS, USER_ALREADY_EXISTS, USER_NOT_FOUND, ERROR
	}

	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public User getUserByEmail(String email) {
		return userRepository.findByEmail(email).orElse(null);
	}

	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	public UserServiceResult createUser(User user) {
		if (userRepository.findByEmail(user.getEmail()).isPresent()) {
			return UserServiceResult.USER_ALREADY_EXISTS;
		}
		try {
			return userRepository.save(user) == null ? UserServiceResult.ERROR : UserServiceResult.SUCCESS;
		} catch (Exception e) {
			return UserServiceResult.ERROR;
		}
	}

	public UserServiceResult updateUser(User user) {
	    return userRepository.findByEmail(user.getEmail()).map(existingUser -> {
	        boolean updated = false;
	        if (!Objects.equals(existingUser.getFirstName(), user.getFirstName())) {
	            existingUser.setFirstName(user.getFirstName());
	            updated = true;
	        }
	        if (!Objects.equals(existingUser.getLastName(), user.getLastName())) {
	            existingUser.setLastName(user.getLastName());
	            updated = true;
	        }
	        if (!Objects.equals(existingUser.getEmail(), user.getEmail())) {
	            existingUser.setEmail(user.getEmail());
	            updated = true;
	        }
	        if (!Objects.equals(existingUser.getPassword(), user.getPassword())) {
	            existingUser.setPassword(user.getPassword());
	            updated = true;
	        }
	        if (!Objects.equals(existingUser.getNationality(), user.getNationality())) {
	            existingUser.setNationality(user.getNationality());
	            updated = true;
	        }
	        if (!Objects.equals(existingUser.getBirthDate(), user.getBirthDate())) {
	            existingUser.setBirthDate(user.getBirthDate());
	            updated = true;
	        }
	        if (existingUser.getRole() != user.getRole()) {
	            existingUser.setRole(user.getRole());
	            updated = true;
	        }
	        if (!Objects.equals(existingUser.getTickets(), user.getTickets())) {
	            existingUser.setTickets(new HashSet<>(user.getTickets()));
	            updated = true;
	        }
	        if (updated) {
	            userRepository.save(existingUser);
	        }
	        return UserServiceResult.SUCCESS;
	    }).orElse(UserServiceResult.USER_NOT_FOUND);
	}

	public UserServiceResult deleteUser(String email) {
		return userRepository.findByEmail(email).map(user -> {
			userRepository.delete(user);
			return UserServiceResult.SUCCESS;
		}).orElse(UserServiceResult.USER_NOT_FOUND);
	}

	public UserServiceResult deleteAllUsers() {
		userRepository.deleteAll();
		return UserServiceResult.SUCCESS;
	}

	public boolean checkUser(String email) {
		return userRepository.findByEmail(email).isPresent();
	}

	public boolean checkPassword(String email, String password) {
		return userRepository.findByEmail(email).map(User::getPassword).filter(pwd -> pwd.equals(password)).isPresent();
	}
}