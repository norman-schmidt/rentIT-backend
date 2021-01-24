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

import com.rentit.project.models.ImageEntity;
import com.rentit.project.models.RentalEntity;
import com.rentit.project.models.UserEntity;
import com.rentit.project.services.ImageService;
import com.rentit.project.services.RentalService;
import com.rentit.project.services.UserService;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private RentalService rentalService;

	@Autowired
	private ImageService imageService;

	@GetMapping("")
	public List<UserEntity> getAllUsers() {
		return userService.getAllUsers();
	}

	@GetMapping("{id}")
	public UserEntity getUserById(@PathVariable long id) {
		return userService.getUser(id);
	}

//	@PostMapping("")
//	public UserEntity addUser(@RequestBody UserEntity userEntity) {
//		return userService.addUser(userEntity);
//	}

	@PutMapping("{id}")
	public UserEntity updateUser(@RequestBody UserEntity userEntity, @PathVariable long id) {

		UserEntity _userEntity = userService.getUser(id);

		_userEntity.setEmail(userEntity.getEmail());
		_userEntity.setLastname(userEntity.getLastname());
		_userEntity.setPassword(userEntity.getPassword());
		_userEntity.setFirstname(userEntity.getFirstname());
		_userEntity.setAddress(userEntity.getAddress());
		_userEntity.setBirthday(userEntity.getBirthday());
		_userEntity.setImage(imageService.updateImage(_userEntity.getImage()));

		return userService.updateUser(_userEntity);

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
		UserEntity user = userService.getUser(id_user);
		RentalEntity rental = rentalService.getRental(id_rental);
		user.getRental().add(rental);
		rental.setUsers(user);
		userService.updateUser(user);
		rentalService.updateRental(rental);
		return user;
	}

	@PutMapping("{id_user}/rental/{id_rental}/remove")
	public UserEntity removeUserRental(@PathVariable long id_user, @PathVariable long id_rental) {
		UserEntity user = userService.getUser(id_user);
		RentalEntity rental = rentalService.getRental(id_rental);
		user.getRental().remove(rental);
		rental.setUsers(user);
		userService.updateUser(user);
		rentalService.updateRental(rental);
		return user;
	}

	@PutMapping("{id_user}/image/{id_image}/add")
	public UserEntity addUserImage(@PathVariable long id_user, @PathVariable long id_image) {
		UserEntity user = userService.getUser(id_user);
		ImageEntity image = imageService.getImage(id_image);
		user.setImage(image);
		image.setUser(user);
		userService.updateUser(user);
		imageService.updateImage(image);
		return user;
	}

}
