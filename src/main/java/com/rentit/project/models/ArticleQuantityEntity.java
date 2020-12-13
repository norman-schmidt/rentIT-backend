package com.rentit.project.models;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
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

	private int quantity;

	@Column(name = "articleReturnedDate")
	private Date articleReturnedDate;

	@ManyToOne
	@JoinColumn(name="articleId")
	private ArticleEntity articles;

	@ManyToOne
	@JoinColumn(name = "rentalId")
	private RentalEntity rental;

}
