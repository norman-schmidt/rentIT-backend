package com.rentit.project.models;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "userId")
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "userId")
	private long userId;

	@NotBlank
	@Size(max = 50)
	@Email
	@Column(name = "email")
	private String email;

	private String username;

	@NotBlank
	@Size(max = 120)
	@Column(name = "password")
	private String password;

	@Column(name = "lastname")
	private String lastname;

	@Column(name = "firstname")
	private String firstname;

	@Size(min = 1, max = 40)
	@Column(name = "street")
	private String street;

	@Size(min = 1, max = 20)
	@Column(name = "hausNumber")
	private String hausNumber;

	@Column(name = "plz")
	private int plz;

	@Size(min = 1, max = 20)
	@Column(name = "ort")
	private String ort;

	@Column(name = "birthday")
	private Date birthday;

	// @OneToMany(mappedBy = "users", fetch = FetchType.LAZY)
	// private List<RentalEntity> rental;

	@JsonIdentityReference(alwaysAsId = true)
	@OneToMany(mappedBy = "users", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<RentalEntity> rental;

	// @OneToOne(mappedBy = "user", cascade = { CascadeType.ALL })
	// private ImageEntity image;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "image_Id")
	private ImageEntity image;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "userId"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
	private Set<Role> roles = new HashSet<>();

	public UserEntity(@NotBlank @Size(max = 50) @Email String email, @NotBlank @Size(max = 120) String password,
			String lastname, String firstname, String street, String hausNumber, int plz, String ort, Date birthday,
			List<RentalEntity> rental, ImageEntity image) {

		this.email = email;
		this.username = email;
		this.password = password;
		this.lastname = lastname;
		this.firstname = firstname;
		this.street = street;
		this.hausNumber = hausNumber;
		this.plz = plz;
		this.ort = ort;
		this.birthday = birthday;
		this.rental = rental;
		this.image = image;
	}

}
