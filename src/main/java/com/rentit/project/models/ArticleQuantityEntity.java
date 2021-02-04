package com.rentit.project.models;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "articleQuantityId")
public class ArticleQuantityEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "articleQuantityId")
	private long articleQuantityId;

	@JsonIdentityReference(alwaysAsId = true)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "article_id")
	private ArticleEntity article;

	@JsonIdentityReference(alwaysAsId = true)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "rental_id")
	private RentalEntity rental;

	@Column(name = "subTotal")
	private double subTotal;

	@Column(name = "quantity")
	private int quantity;

	@Column(name = "returned")
	private boolean returned;

	@Column(name = "returnDate")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private LocalDateTime returnDate;

	@Column(name = "ReturnedDate")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private LocalDateTime ReturnedDate;
	
	@Column(name = "rentalDate")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private LocalDateTime rentalDate;

}