package com.rentit.project.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.rentit.project.models.InvoiceEntity;
import com.rentit.project.repositories.InvoiceRepository;

@Service
public class InvoiceService {

	@Autowired
	private InvoiceRepository invoiceRepository;

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
		_invoiceEntity = invoiceEntity;
		return invoiceRepository.save(_invoiceEntity);
	}

}
