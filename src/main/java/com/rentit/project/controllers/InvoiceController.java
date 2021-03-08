package com.rentit.project.controllers;

import java.util.List;

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
import com.rentit.project.pojo.response.MessageResponse;
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
	public ResponseEntity<MessageResponse> removeInvoice(@PathVariable Long id) {
		invoiceService.deleteInvoice(id);
		return ResponseEntity.ok().body(new MessageResponse("Successfully deleted"));
	}

}
