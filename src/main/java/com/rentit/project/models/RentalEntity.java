package com.rentit.project.models;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RentalEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "rentalId")
	private long rentalId;

	@Column(name = "rentDate")
	private Date rentDate;

	@Column(name = "returnDate")
	private Date returnDate;

	@Column(name = "amount")
	private long amount;

	@ManyToOne(fetch = FetchType.LAZY )
	@JoinColumn(name = "userId")
	private UserEntity users;

	@OneToMany(mappedBy = "rental", fetch = FetchType.LAZY ) // cascade or not
	private List<ArticleEntity> articles;

	@OneToOne
	@JoinColumn(name = "invoiceId")
	private InvoiceEntity invoice;

}
