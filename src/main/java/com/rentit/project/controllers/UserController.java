package com.rentit.project.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rentit.project.models.UserEntity;
import com.rentit.project.pojo.response.MessageResponse;
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
	
	@GetMapping("registredUser")
	public UserEntity getRegistredUser(@RequestHeader(value = "Authorization") String authHeader) {
		return userService.getUserFromToken(authHeader);//.getUserId();
	}

	@DeleteMapping("{id}")
	public ResponseEntity<Map<String, Boolean>> removeCategory(@PathVariable Long id) {
		userService.deleteUser(id);
		Map<String, Boolean> response = new HashMap<>();
		response.put("Successfully deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}

	@PutMapping("{id}")
	public UserEntity updateUser(@RequestBody UserEntity userEntity, @PathVariable long id) {
		return userService.updateUser(userEntity, id);
	}

	@PutMapping("{id_user}/image/{id_image}/add")
	public UserEntity addUserImage(@PathVariable long id_user, @PathVariable long id_image) {
		return userService.addUserImage(id_user, id_image);
	}

	@PatchMapping("content") // soll from Token
	public ResponseEntity<MessageResponse> updateUserElement(@RequestHeader(value = "Authorization") String authHeader,
			@RequestBody Map<String, Object> userEntity) {
		// userId from Token and Body
		return userService.updateUserElement(userService.getUserFromToken(authHeader).getUserId(), userEntity);
	}

	@PatchMapping("password")
	public ResponseEntity<MessageResponse> updateUserPassword(@RequestHeader(value = "Authorization") String authHeader,
			@RequestBody Map<String, Object> userEntity) {

		return userService.updateUserPwd(userService.getUserFromToken(authHeader).getUserId(), userEntity);
	}

	@PatchMapping("passwordForget")
	public ResponseEntity<MessageResponse> UserForgetPassword(@RequestHeader(value = "Authorization") String authHeader,
			@RequestBody Map<String, Object> userEntity) {

		return userService.UserForgetPwd(userService.getUserFromToken(authHeader).getUserId(), userEntity);
	}

}
