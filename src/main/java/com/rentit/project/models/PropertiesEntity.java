package com.rentit.project.models;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "propertiesId")
public class PropertiesEntity {// implements Serializable

	/**
	 * 
	 */
	// private static final long serialVersionUID = 784834265734499445L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "propertiesId")
	private long propertiesId;

	@Column(name = "storage")
	private int storage;

	@Column(name = "operatingSystem")
	private String operatingSystem;

	@Column(name = "color")
	private String color;

	@Column(name = "specialFeature")
	private String specialFeature;

	@Column(name = "manifacturer")
	private String manifacturer;

	@OneToOne(mappedBy = "propreties", fetch = FetchType.EAGER)
	private ArticleEntity article;

}