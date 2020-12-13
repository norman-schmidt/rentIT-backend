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

import com.rentit.project.models.InvoiceEntity;
import com.rentit.project.models.RentalEntity;
import com.rentit.project.models.UserEntity;
import com.rentit.project.services.ArticleService;
import com.rentit.project.services.InvoiceService;
import com.rentit.project.services.RentalService;
import com.rentit.project.services.UserService;

@RestController
@RequestMapping("/api/rentals")
public class RentalController {

	@Autowired
	private UserService userService;

	@Autowired
	private ArticleService articleService;

	@Autowired
	private InvoiceService invoiceService;

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
		return rentalService.addRental(rentalEntity);
	}

	@PutMapping("{id}")
	public RentalEntity updateRental(@RequestBody RentalEntity rentalEntity, @PathVariable long id) {

		RentalEntity _rentalEntity = rentalService.getRental(id);

		_rentalEntity.setRentDate(rentalEntity.getRentDate());
		_rentalEntity.setReturnDate(rentalEntity.getReturnDate());
		_rentalEntity.setTotalPrice((rentalEntity.getTotalPrice()));
		_rentalEntity.setUsers(userService.updateUser(_rentalEntity.getUsers()));
		_rentalEntity.setInvoice(invoiceService.updateInvoice(_rentalEntity.getInvoice()));
		return rentalService.updateRental(_rentalEntity);
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
		UserEntity user = userService.getUser(id_user);
		RentalEntity rent = rentalService.getRental(id_rental);
		rent.setUsers(user);
		user.getRental().add(rent);
		userService.updateUser(user);
		rentalService.updateRental(rent);

		return rent;
	}

	@PutMapping("{id_rental}/user/{id_user}/remove")
	public RentalEntity removeRentalUser(@PathVariable long id_rental, @PathVariable long id_user) {
		UserEntity user = userService.getUser(id_user);
		RentalEntity rent = rentalService.getRental(id_rental);
		rent.setUsers(user);
		user.getRental().remove(rent);
		userService.updateUser(user);
		rentalService.updateRental(rent);

		return rent;
	}

	@PutMapping("{id_rental}/invoice/{id_invoice}")
	public RentalEntity addRentalInvoice(@PathVariable long id_rental, @PathVariable long id_invoice) {
		RentalEntity rent = rentalService.getRental(id_rental);
		InvoiceEntity invoice = invoiceService.getInvoice(id_invoice);
		rent.setInvoice(invoice);
		invoice.setRental(rent);
		rentalService.updateRental(rent);
		invoiceService.updateInvoice(invoice);
		return rent;
	}

}
