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


}
