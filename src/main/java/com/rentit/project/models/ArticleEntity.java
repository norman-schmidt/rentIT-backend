package com.rentit.project.models;

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

	@OneToOne(mappedBy = "article", fetch = FetchType.EAGER)
	private PropertiesEntity propreties;

	@ManyToOne
	@JoinColumn(name = "rentalId")
	private RentalEntity rental;

	@ManyToOne
	@JoinColumn(name = "categoryId")
	private CategoryEntity category;

	@OneToMany(mappedBy = "article", fetch = FetchType.EAGER)
	private List<ImageEntity> images;

}
