package com.rentit.project.controllers;

import java.util.ArrayList;
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

	@GetMapping("all")
	public List<RentalEntity> getAllRental() {
		return rentalService.getAllRentals();
	}

	@GetMapping("{id}")
	public RentalEntity getRentalById(@PathVariable long id) {
		return rentalService.getRental(id);
	}

	@PostMapping("add")
	public RentalEntity addRental(@RequestBody RentalEntity rentalEntity) {
		return rentalService.addRental(rentalEntity);
	}

	@PutMapping("update/{id}")
	public RentalEntity updateRental(@RequestBody RentalEntity rentalEntity, @PathVariable long id) {

		RentalEntity _rentalEntity = rentalService.getRental(id);

		_rentalEntity.setRentDate(rentalEntity.getRentDate());
		_rentalEntity.setReturnDate(rentalEntity.getReturnDate());
		_rentalEntity.setAmount(rentalEntity.getAmount());
		_rentalEntity.setUsers(rentalEntity.getUsers());
		_rentalEntity.setArticles(rentalEntity.getArticles());
		_rentalEntity.setInvoice(rentalEntity.getInvoice());
		return rentalService.updateRental(_rentalEntity);
	}

	@PutMapping("update/{id_rental}/{id_user}")
	public RentalEntity setRentalUser(@PathVariable long id_rental, @PathVariable long id_user) {
		UserEntity user = userService.getUser(id_user);
		RentalEntity rent = rentalService.getRental(id_rental);
		List<RentalEntity> list = new ArrayList<RentalEntity>();
		list.add(rent);
		rent.setUsers(user);
		user.setRental(list);

		return rent;
	}

	@DeleteMapping("remove/{id}")
	public ResponseEntity<Map<String, Boolean>> removeRental(@PathVariable Long id) {

		rentalService.deleteRental(id);

		Map<String, Boolean> response = new HashMap<>();
		response.put("Successfully deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}

}
