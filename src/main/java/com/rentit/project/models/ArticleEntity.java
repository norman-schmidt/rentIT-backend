package com.rentit.project.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
	private long articleId;

	@Column(name="name")
	private String name;

	@Column(name="serialNumber")
	private String serialNumber;

	@Column(name="model")
	private String model;

	@Column(name="stockLevel")
	private int stockLevel;

	@Column(name="price")
	private double price;

}
