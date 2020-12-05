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
import com.rentit.project.services.InvoiceService;
import com.rentit.project.services.RentalService;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

	@Autowired
	private RentalService rentalService;

	@Autowired
	private InvoiceService invoiceService;

	@GetMapping("all")
	public List<InvoiceEntity> getAllInvoice() {
		return invoiceService.getAllInvoices();
	}

	@GetMapping("{id}")
	public InvoiceEntity getInvoiceById(@PathVariable long id) {
		return invoiceService.getInvoice(id);
	}

	@PostMapping("add")
	public InvoiceEntity addInvoice(@RequestBody InvoiceEntity invoiceEntity) {
		return invoiceService.addInvoice(invoiceEntity);
	}

	@PutMapping("update/{id}")
	public InvoiceEntity updateInvoice(@RequestBody InvoiceEntity invoiceEntity, @PathVariable long id) {

		InvoiceEntity _invoiceEntity = invoiceService.getInvoice(id);

		_invoiceEntity.setInvoiceNumber(invoiceEntity.getInvoiceNumber());
		_invoiceEntity.setInvoiceDate(invoiceEntity.getInvoiceDate());

		_invoiceEntity.setRental(rentalService.updateRental(_invoiceEntity.getRental()));

		return invoiceService.updateInvoice(_invoiceEntity);
	}

	@DeleteMapping("remove/{id}")
	public ResponseEntity<Map<String, Boolean>> removeInvoice(@PathVariable Long id) {

		invoiceService.deleteInvoice(id);

		Map<String, Boolean> response = new HashMap<>();
		response.put("Successfully deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}

	@PutMapping("update/{id_invoice}/rental/{id_rental}")
	public InvoiceEntity setInvoiceRental(@PathVariable long id_invoice, @PathVariable long id_rental) {
		InvoiceEntity invoice = invoiceService.getInvoice(id_invoice);
		RentalEntity rental = rentalService.getRental(id_rental);
		invoice.setRental(rental);
		rental.setInvoice(invoice);
		invoiceService.updateInvoice(invoice);
		rentalService.updateRental(rental);
		return invoice;
	}

}
