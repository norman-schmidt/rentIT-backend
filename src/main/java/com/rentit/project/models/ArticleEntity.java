package com.rentit.project.models;

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

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "articleId")
public class ArticleEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "articleId")
	private long articleId;

	@Column(name = "name")
	private String name;

	@Column(name = "serialNumber")
	private String serialNumber;

	@Column(name = "model")
	private String model;

	@Column(name = "stockLevel")
	private int stockLevel;

	@Column(name = "price")
	private double price;

	@OneToOne
	private PropertiesEntity propreties;

	@ManyToOne(cascade = { CascadeType.ALL })
	@JoinColumn(name = "rentalId")
	private RentalEntity rental;

	@ManyToOne(cascade = { CascadeType.ALL })
	@JoinColumn(name = "categoryId")
	private CategoryEntity category;

	@OneToMany(mappedBy = "article", fetch = FetchType.LAZY)
	private List<ImageEntity> images;

}
