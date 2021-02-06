package com.rentit.project.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.rentit.project.models.InvoiceEntity;
import com.rentit.project.models.RentalEntity;
import com.rentit.project.models.UserEntity;
import com.rentit.project.repositories.RentalRepository;

@Service
public class RentalService {

	@Autowired
	private RentalRepository rentalRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private InvoiceService invoiceService;

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

	public RentalEntity updateRental(RentalEntity rentalEntity, long id) {

		RentalEntity _rentalEntity = getRental(id);

		_rentalEntity.setRentDate(rentalEntity.getRentDate());
		_rentalEntity.setTotalPrice((rentalEntity.getTotalPrice()));
		_rentalEntity.setUsers(userService.updateUser(_rentalEntity.getUsers(), _rentalEntity.getUsers().getUserId()));
		_rentalEntity.setInvoice(
				invoiceService.updateInvoice(_rentalEntity.getInvoice(), _rentalEntity.getInvoice().getInvoiceId()));

		return rentalRepository.save(rentalEntity);
	}

	public List<RentalEntity> updateRental(List<RentalEntity> rental) {
		return rentalRepository.saveAll(rental);
	}

	public RentalEntity addRentalUser(long id_rental, long id_user) {
		UserEntity user = userService.getUser(id_user);
		RentalEntity rent = getRental(id_rental);
		rent.setUsers(user);
		user.getRental().add(rent);
		userService.updateUser(user, id_user);
		updateRental(rent, id_rental);
		return rent;
	}

	public RentalEntity removeRentalUser(long id_rental, long id_user) {
		UserEntity user = userService.getUser(id_user);
		RentalEntity rent = getRental(id_rental);
		rent.setUsers(user);
		user.getRental().remove(rent);
		userService.updateUser(user, id_user);
		updateRental(rent, id_rental);

		return rent;
	}

	public RentalEntity addRentalInvoice(long id_rental, long id_invoice) {
		RentalEntity rent = getRental(id_rental);
		InvoiceEntity invoice = invoiceService.getInvoice(id_invoice);
		rent.setInvoice(invoice);
		invoice.setRental(rent);
		updateRental(rent, id_rental);
		invoiceService.updateInvoice(invoice, id_invoice);
		return rent;
	}

}
