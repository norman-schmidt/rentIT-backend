package com.rentit.project.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rentit.project.models.UserEntity;
import com.rentit.project.services.UserService;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("")
	public List<UserEntity> getAllUsers() {
		return userService.getAllUsers();
	}

	@GetMapping("{id}")
	public UserEntity getUserById(@PathVariable long id) {
		return userService.getUser(id);
	}

	@PutMapping("{id}")
	public UserEntity updateUser(@RequestBody UserEntity userEntity, @PathVariable long id) {
		return userService.updateUser(userEntity, id);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<Map<String, Boolean>> removeCategory(@PathVariable Long id) {
		userService.deleteUser(id);
		Map<String, Boolean> response = new HashMap<>();
		response.put("Successfully deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}

	@PutMapping("{id_user}/rental/{id_rental}/add")
	public UserEntity addUserRental(@PathVariable long id_user, @PathVariable long id_rental) {
		return userService.addUserRental(id_user, id_rental);
	}

	@PutMapping("{id_user}/rental/{id_rental}/remove")
	public UserEntity removeUserRental(@PathVariable long id_user, @PathVariable long id_rental) {
		return userService.removeUserRental(id_user, id_rental);
	}

	@PutMapping("{id_user}/image/{id_image}/add")
	public UserEntity addUserImage(@PathVariable long id_user, @PathVariable long id_image) {
		return userService.addUserImage(id_user, id_image);
	}

}
