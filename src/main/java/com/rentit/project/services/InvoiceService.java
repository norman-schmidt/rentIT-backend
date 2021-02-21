package com.rentit.project.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.rentit.project.models.InvoiceEntity;
import com.rentit.project.models.RentalEntity;
import com.rentit.project.repositories.InvoiceRepository;

@Service
public class InvoiceService {

	@Autowired
	private InvoiceRepository invoiceRepository;

	@Autowired
	private RentalService rentalService;

	public InvoiceEntity addInvoice(InvoiceEntity invoice) {
		return invoiceRepository.save(invoice);
	}

	public InvoiceEntity getInvoice(Long id) {
		return invoiceRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}

	public List<InvoiceEntity> getAllInvoices() {
		return invoiceRepository.findAll();
	}

	public void deleteInvoice(Long id) {
		InvoiceEntity invoice = getInvoice(id);
		invoiceRepository.delete(invoice);
	}

	public InvoiceEntity updateInvoice(InvoiceEntity invoiceEntity, long id) {

		InvoiceEntity _invoiceEntity = getInvoice(id);

		_invoiceEntity.setInvoiceNumber(invoiceEntity.getInvoiceNumber());
		_invoiceEntity.setInvoiceDate(invoiceEntity.getInvoiceDate());

		_invoiceEntity.setRental(
				rentalService.updateRental(_invoiceEntity.getRental(), _invoiceEntity.getRental().getRentalId()));

		return invoiceRepository.save(invoiceEntity);
	}

	public InvoiceEntity setInvoiceRental(long id_invoice, long id_rental) {
		InvoiceEntity invoice = getInvoice(id_invoice);
		RentalEntity rental = rentalService.getRental(id_rental);
		invoice.setRental(rental);
		rental.setInvoice(invoice);
		updateInvoice(invoice, id_invoice);
		rentalService.updateRental(rental, id_rental);
		return invoice;
	}

}
