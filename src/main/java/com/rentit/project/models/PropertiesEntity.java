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
public class PropertiesEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long propertiesId;

	private int storage;

	private String operatingSystem;

	private String color;

	private String specialFeature;

	private String manifacturer;

}
