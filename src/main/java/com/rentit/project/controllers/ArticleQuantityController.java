package com.rentit.project.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rentit.project.models.ArticleQuantityEntity;
import com.rentit.project.models.UserEntity;
import com.rentit.project.pojo.query.CustomPojoReturn;
import com.rentit.project.pojo.response.MessageResponse;
import com.rentit.project.services.ArticleQuantityService;
import com.rentit.project.services.UserService;

@RestController
@RequestMapping("/api/quantities")
@CrossOrigin(origins = "*")
public class ArticleQuantityController {

	@Autowired
	private ArticleQuantityService quantityService;

	@Autowired
	private UserService userService;

	@GetMapping("")
	public List<ArticleQuantityEntity> getAllQuantity() {
		return quantityService.getAllArticleQuantitys();
	}

	@GetMapping("{id}")
	public ArticleQuantityEntity getQuantityById(@PathVariable long id) {
		return quantityService.getArticleQuantity(id);
	}

	@GetMapping("listRental")
	public List<CustomPojoReturn> getListRentalUser(@RequestHeader(value = "Authorization") String authHeader) {
		UserEntity user = userService.getUserFromToken(authHeader);
		return quantityService.getListRentalUser(user.getUserId());
	}

	// User from token

	@PostMapping("")
	public ResponseEntity<MessageResponse> addQuantity(@RequestBody List<ArticleQuantityEntity> quantityEntity,
			@RequestHeader(value = "Authorization") String authHeader) {
		return quantityService.addArticleQuantity(quantityEntity, authHeader);
	}

	@PostMapping("return")
	public ResponseEntity<MessageResponse> returnArticle(@RequestBody Map<String, ArrayList<Long>> data,
			@RequestHeader(value = "Authorization") String authHeader) {
		return quantityService.returnArticle(data, authHeader);
	}

	// User from token

	@PutMapping("{id}")
	public ArticleQuantityEntity updateQuantity(@RequestBody ArticleQuantityEntity quantityEntity,
			@PathVariable long id) {
		return quantityService.updateArticleQuantities(quantityEntity, id);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<MessageResponse> removeQuantity(@PathVariable Long id) {
		return quantityService.deleteArticleQuantity(id);
	}

}
