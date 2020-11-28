package com.rentit.project.models;

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
public class ArtikelEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long articleId;

	private String name;

	private String serialNumber;

	private String model;

	private int stockLevel;

	private double price;

}
