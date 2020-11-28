package com.rentit.project.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "invoiceId")
	private long invoiceId;

	@Column(name = "invoiceNumber")
	private int invoiceNumber;

	@Column(name = "invoiceDate")
	private Date invoiceDate;

	@OneToOne(mappedBy = "invoice")
	private RentalEntity rental;

}
