package com.rentit.project.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.rentit.project.models.RentalEntity;
import com.rentit.project.repositories.RentalRepository;

@Service
public class RentalService {

	@Autowired
	private RentalRepository rentalRepository;

	public RentalEntity addRental(RentalEntity rental) {
		return rentalRepository.save(rental);
	}

	public RentalEntity getRental(Long id) {
		return rentalRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}

	public List<RentalEntity> getAllRentals() {
		return rentalRepository.findAll();
	}

	public void deleteRental(Long id) {
		RentalEntity rental = getRental(id);
		rentalRepository.delete(rental);
	}

	public RentalEntity updateRental(RentalEntity rental) {
		return rentalRepository.save(rental);
	}
	
	public List<RentalEntity> updateRental(List<RentalEntity> rental) {
		return rentalRepository.saveAll(rental);
	}

}
