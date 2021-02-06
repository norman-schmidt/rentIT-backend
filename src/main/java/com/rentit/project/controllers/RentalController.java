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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rentit.project.models.RentalEntity;
import com.rentit.project.services.RentalService;

@RestController
@RequestMapping("/api/rentals")
@CrossOrigin(origins = "*")
public class RentalController {

	@Autowired
	private RentalService rentalService;

	@GetMapping("")
	public List<RentalEntity> getAllRental() {
		return rentalService.getAllRentals();
	}

	@GetMapping("{id}")
	public RentalEntity getRentalById(@PathVariable long id) {
		return rentalService.getRental(id);
	}

	@PostMapping("")
	public RentalEntity addRental(@RequestBody RentalEntity rentalEntity) {

		/*
		 * RentalEntity rentalEntity_ = rentalEntity;
		 * rentalEntity_.setArticleQuantity(null);
		 * rentalService.addRental(rentalEntity_);
		 * 
		 * List<ArticleQuantityEntity> quantityEntity =
		 * rentalEntity.getArticleQuantity();
		 * quantityService.addArticleQuantity(quantityEntity.get(0));
		 * 
		 * rentalEntity_.setArticleQuantity(quantityEntity);
		 * rentalService.addRental(rentalEntity_);
		 * 
		 * Date date = new Date(System.currentTimeMillis()); // invoiceEntity
		 * InvoiceEntity invoiceEntity = new InvoiceEntity();
		 * invoiceEntity.setInvoiceDate(date); invoiceEntity.setInvoiceNumber(1);//!?
		 * invoiceService.addInvoice(invoiceEntity);
		 * 
		 * rentalEntity.setInvoice(invoiceEntity); List<ArticleQuantityEntity>
		 * quantityEntity = rentalEntity.getArticleQuantity();
		 * quantityService.addArticleQuantity(quantityEntity.get(0));
		 */

		return rentalService.addRental(rentalEntity);
	}

	@PutMapping("{id}")
	public RentalEntity updateRental(@RequestBody RentalEntity rentalEntity, @PathVariable long id) {
		return rentalService.updateRental(rentalEntity, id);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<Map<String, Boolean>> removeRental(@PathVariable Long id) {
		rentalService.deleteRental(id);
		Map<String, Boolean> response = new HashMap<>();
		response.put("Successfully deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}

	@PutMapping("{id_rental}/user/{id_user}/add")
	public RentalEntity addRentalUser(@PathVariable long id_rental, @PathVariable long id_user) {
		return rentalService.addRentalUser(id_rental, id_user);
	}

	@PutMapping("{id_rental}/user/{id_user}/remove")
	public RentalEntity removeRentalUser(@PathVariable long id_rental, @PathVariable long id_user) {
		return rentalService.removeRentalUser(id_rental, id_user);
	}

	@PutMapping("{id_rental}/invoice/{id_invoice}")
	public RentalEntity addRentalInvoice(@PathVariable long id_rental, @PathVariable long id_invoice) {
		return rentalService.addRentalInvoice(id_rental, id_invoice);

	}

}
