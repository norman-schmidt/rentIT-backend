package com.rentit.project.controllers;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.internal.build.AllowSysOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rentit.project.models.ArticleQuantityEntity;
import com.rentit.project.models.InvoiceEntity;
import com.rentit.project.models.RentalEntity;
import com.rentit.project.services.ArticleQuantityService;
import com.rentit.project.services.ArticleService;
import com.rentit.project.services.InvoiceService;
import com.rentit.project.services.RentalService;

@RestController
@RequestMapping("/api/quantities")
@CrossOrigin(origins = "*")
public class ArticleQuantityController {

	@Autowired
	private RentalService rentalService;

	@Autowired
	private ArticleService articleService;

	@Autowired
	private InvoiceService invoiceService;

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

		Date date = new Date(System.currentTimeMillis());
		Date dateReturn = new Date(System.currentTimeMillis() + (30L * 24 * 60 * 60 * 1000)); // 1 month

		// invoiceEntity
		InvoiceEntity invoiceEntity = new InvoiceEntity();
		invoiceEntity.setInvoiceDate(date);
		invoiceEntity.setInvoiceNumber(1);
		invoiceService.addInvoice(invoiceEntity);

		// rentalEntity
		RentalEntity rentalEntity = new RentalEntity();
		rentalEntity.setInvoice(invoiceEntity);
		rentalEntity.setRentDate(date);
		rentalEntity.setReturnDate(dateReturn);

		// rentalEntity.setUsers(users);

		// hole ArticleId von quantityEntity
		// hole article
		// hole price
		double totalPrice = quantityEntity.getQuantity()
				* articleService.getArticle(quantityEntity.getArticle().getArticleId()).getPrice();
		rentalEntity.setTotalPrice(totalPrice);
		rentalService.addRental(rentalEntity);

		quantityEntity.setRental(rentalEntity);

		return quantityService.addArticleQuantity(quantityEntity);
	}

	@PutMapping("{id}")
	public ArticleQuantityEntity updateQuantity(@RequestBody ArticleQuantityEntity quantityEntity,
			@PathVariable long id) {

		ArticleQuantityEntity _quantityEntity = quantityService.getArticleQuantity(id);

		_quantityEntity.setQuantity(quantityEntity.getQuantity());
		_quantityEntity.setArticleReturnedDate(quantityEntity.getArticleReturnedDate());
		_quantityEntity.setArticle(quantityEntity.getArticle());
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
