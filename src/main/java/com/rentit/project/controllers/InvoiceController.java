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

import com.rentit.project.models.InvoiceEntity;
import com.rentit.project.services.InvoiceService;

@RestController
@RequestMapping("/api/invoices")
@CrossOrigin
public class InvoiceController {

	@Autowired
	private InvoiceService invoiceService;

	@GetMapping("")
	public List<InvoiceEntity> getAllInvoice() {
		return invoiceService.getAllInvoices();
	}

	@GetMapping("{id}")
	public InvoiceEntity getInvoiceById(@PathVariable long id) {
		return invoiceService.getInvoice(id);
	}

	@PostMapping("")
	public InvoiceEntity addInvoice(@RequestBody InvoiceEntity invoiceEntity) {
		return invoiceService.addInvoice(invoiceEntity);
	}

	@PutMapping("{id}")
	public InvoiceEntity updateInvoice(@RequestBody InvoiceEntity invoiceEntity, @PathVariable long id) {
		return invoiceService.updateInvoice(invoiceEntity, id);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<Map<String, Boolean>> removeInvoice(@PathVariable Long id) {
		invoiceService.deleteInvoice(id);
		Map<String, Boolean> response = new HashMap<>();
		response.put("Successfully deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}

	@PutMapping("{id_invoice}/rental/{id_rental}")
	public InvoiceEntity setInvoiceRental(@PathVariable long id_invoice, @PathVariable long id_rental) {
		return invoiceService.setInvoiceRental(id_invoice, id_rental);
	}

}
