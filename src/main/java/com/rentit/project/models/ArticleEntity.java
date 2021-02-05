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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

	@Lob
	@Column(name = "description")
	private String description;

	@Column(name = "serialNumber")
	private String serialNumber;

	@Column(name = "model")
	private String model;

	@Column(name = "stockLevel")
	private int stockLevel;

	@Column(name = "price")
	private double price;

	//@JsonIdentityReference(alwaysAsId = true)
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "properties_id")
	private PropertiesEntity properties;

	@JsonIdentityReference(alwaysAsId = true)
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "category_Id")
	private CategoryEntity category;

	@OneToMany(mappedBy = "art", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<ImageEntity> images;

	@JsonIgnore
	@JsonIdentityReference(alwaysAsId = true)
	@OneToMany(mappedBy = "article", fetch = FetchType.LAZY)
	private List<ArticleQuantityEntity> articleQuantity;

}