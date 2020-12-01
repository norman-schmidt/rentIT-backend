package com.rentit.project.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

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

	@OneToOne(fetch = FetchType.LAZY )
	@JoinColumn(name = "articleId")
	private ArticleEntity article;

}
