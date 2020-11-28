package com.rentit.project.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "imageId")
	private long imageId;

	@Column(name = "imageLink")
	private String imageLink;

	@Column(name = "imageType")
	private String imageType;
	
	
	@OneToOne(mappedBy = "image")
	private UserEntity user;

	@ManyToOne
	@JoinColumn(name = "image_Id")
	private  ArticleEntity article;

}
