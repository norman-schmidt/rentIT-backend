package com.rentit.project.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.rentit.project.models.UserEntity;
import com.rentit.project.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public UserEntity addUser(UserEntity user) {
		return userRepository.save(user);
	}

	public UserEntity getUser(Long id) {
		return userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}
	
	public UserEntity findUserByEmail(String email) {
		return userRepository.findByEmail(email).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}

	public List<UserEntity> getAllUsers() {
		return userRepository.findAll();
	}

	public void deleteUser(Long id) {
		UserEntity user = getUser(id);
		userRepository.delete(user);
	}

	public UserEntity updateUser(UserEntity user) {
		return userRepository.save(user);
	}

}
