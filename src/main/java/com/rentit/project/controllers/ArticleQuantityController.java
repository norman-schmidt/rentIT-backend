package com.rentit.project.controllers;

import java.util.Date;
import java.util.HashMap;
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
	// public ArticleQuantityEntity addQuantity(@RequestBody
	// List<ArticleQuantityEntity> quantityEntity) {
	public ResponseEntity<Map<String, Boolean>> addQuantity(@RequestBody List<ArticleQuantityEntity> quantityEntity) {
		Date date = new Date(System.currentTimeMillis());
		Date dateReturn = new Date(System.currentTimeMillis() + (30L * 24 * 60 * 60 * 1000)); // 1 month
		double subTotal = 0.0; // subTotal for quantityEntity
		double totalPrice = 0.0; // totalPrice for rental

		// invoiceEntity
		InvoiceEntity invoiceEntity = new InvoiceEntity();
		invoiceEntity.setInvoiceDate(date);
		invoiceEntity.setInvoiceNumber(1);
		invoiceService.addInvoice(invoiceEntity);

		// rentalEntity
		RentalEntity rentalEntity = new RentalEntity();
		rentalEntity.setInvoice(invoiceEntity);
		rentalEntity.setRentDate(date);

		// rentalEntity.setUsers(users); ??

		// hole ArticleId von quantityEntity
		// hole article and price
		// Rechnung totalPrice und subTotal
		for (int i = 0; i < quantityEntity.size(); i++) {
			subTotal = quantityEntity.get(i).getQuantity()
					* articleService.getArticle(quantityEntity.get(i).getArticle().getArticleId()).getPrice();
			quantityEntity.get(i).setSubTotal(subTotal);
			// total
			totalPrice += subTotal;
		}

		rentalEntity.setTotalPrice(totalPrice);
		rentalService.addRental(rentalEntity);

		// update quantityEntity
		for (int i = 0; i < quantityEntity.size(); i++) {
			quantityEntity.get(i).setRental(rentalEntity);
			quantityEntity.get(i).setReturnDate(dateReturn);
			quantityService.addArticleQuantity(quantityEntity.get(i));
		}

		Map<String, Boolean> response = new HashMap<>();
		response.put("Successfully Added", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}

	@PutMapping("{id}")
	public ArticleQuantityEntity updateQuantity(@RequestBody ArticleQuantityEntity quantityEntity,
			@PathVariable long id) {

		ArticleQuantityEntity _quantityEntity = quantityService.getArticleQuantity(id);

		_quantityEntity.setQuantity(quantityEntity.getQuantity());
		_quantityEntity.setArticleReturnedDate(quantityEntity.getArticleReturnedDate());
		_quantityEntity.setArticle(quantityEntity.getArticle());
		_quantityEntity.setReturnDate(quantityEntity.getReturnDate());
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
