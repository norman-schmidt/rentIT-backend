package com.rentit.project.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "imageId")
// @JsonIgnoreProperties({ "user", "art", "imageType"})
@JsonIgnoreProperties(value = { "user", "art", "imageType" }, allowSetters = true)
// aufpassen ohne allowSetters die Attributen werden auch beim Erstellen der Objekte ignoriert
// allowSetters
// ignoreUnknown
// allowGetters
// @JsonIgnore
public class ImageEntity {

	/*
	 * @JsonCreator public ImageEntity(long imageId, @JsonProperty(value = "Link",
	 * required = true) String Link, String imageType, UserEntity user,
	 * ArticleEntity art) { }
	 * 
	 */
	// required = true work only in constructor

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "imageId")
	private long imageId;

	// @JsonProperty(value = "Link")
	// change the name of a field
	// only in Json In-Out
	@Column(name = "imageLink")
	private String imageLink;

	// Titelbild oder Bechreibung-Bild oder vom User
	@Column(name = "imageType")
	private String imageType;

	@JsonIdentityReference(alwaysAsId = true)
	@OneToOne(mappedBy = "image", fetch = FetchType.LAZY)
	private UserEntity user;

	@JsonIdentityReference(alwaysAsId = true)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "article_Id")
	private ArticleEntity art;

}