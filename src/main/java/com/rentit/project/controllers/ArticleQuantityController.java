package com.rentit.project.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rentit.project.models.ArticleQuantityEntity;
import com.rentit.project.services.ArticleQuantityService;
import com.rentit.project.services.RentalService;

@RestController
@RequestMapping("/api/quantities")
public class ArticleQuantityController {

	@Autowired
	private RentalService rentalService;

	@Autowired
	private ArticleQuantityService quantityService;

	@GetMapping("")
	public List<ArticleQuantityEntity> getAllQuantity() {
		return quantityService.getAllArticleQuantitys();
	}

	@GetMapping("{id}")
	public ArticleQuantityEntity getQuantityById(@PathVariable long id) {
		return quantityService.getArticleQuantity(id);
	}

	@PostMapping("")
	public ArticleQuantityEntity addQuantity(@RequestBody ArticleQuantityEntity quantityEntity) {
		return quantityService.addArticleQuantity(quantityEntity);
	}

	@PutMapping("{id}")
	public ArticleQuantityEntity updateQuantity(@RequestBody ArticleQuantityEntity quantityEntity,
			@PathVariable long id) {

		ArticleQuantityEntity _quantityEntity = quantityService.getArticleQuantity(id);

		_quantityEntity.setQuantity(quantityEntity.getQuantity());
		_quantityEntity.setArticleReturnedDate(quantityEntity.getArticleReturnedDate());

		_quantityEntity.setArticles(quantityEntity.getArticles());

		_quantityEntity.setRental(rentalService.updateRental(_quantityEntity.getRental()));

		return quantityService.updateArticleQuantities(_quantityEntity);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<Map<String, Boolean>> removeQuantity(@PathVariable Long id) {

		quantityService.deleteArticleQuantity(id);

		Map<String, Boolean> response = new HashMap<>();
		response.put("Successfully deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}

}
