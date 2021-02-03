package com.rentit.project.models;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
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
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "rentalId")
public class RentalEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "rentalId")
	private long rentalId;

	@Column(name = "rentDate")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	// @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime rentDate;

	@Column(name = "totalPrice")
	private double totalPrice;

	@JsonIdentityReference(alwaysAsId = true)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_Id")
	private UserEntity users;

	@JsonIdentityReference(alwaysAsId = true)
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "invoice_Id")
	private InvoiceEntity invoice;

	@JsonIdentityReference(alwaysAsId = true)
	@OneToMany(mappedBy = "rental", fetch = FetchType.EAGER)
	private List<ArticleQuantityEntity> articleQuantity;

}