package com.rentit.project.pojo.request;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.rentit.project.models.ImageEntity;
import com.rentit.project.models.RentalEntity;

public class SignupRequest {

	@NotBlank
	@Size(max = 50)
	@Email
	private String email;

	@NotBlank
	@Size(min = 6, max = 40)
	private String password;

	@Size(min = 3, max = 20)
	private String lastname;

	@Size(min = 3, max = 20)
	private String firstname;

	@Size(min = 1, max = 20)
	private String street;

	@Size(min = 1, max = 20)
	private String hausNumber;

	private int plz;

	@Size(min = 1, max = 20)
	private String ort;

	private Date birthday;

	private List<RentalEntity> rental;

	private ImageEntity image;

	private Set<String> role;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<String> getRole() {
		return this.role;
	}

	public void setRole(Set<String> role) {
		this.role = role;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getHausNumber() {
		return hausNumber;
	}

	public void setHausNumber(String hausNumber) {
		this.hausNumber = hausNumber;
	}

	public int getPlz() {
		return plz;
	}

	public void setPlz(int plz) {
		this.plz = plz;
	}

	public String getOrt() {
		return ort;
	}

	public void setOrt(String ort) {
		this.ort = ort;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public List<RentalEntity> getRental() {
		return rental;
	}

	public void setRental(List<RentalEntity> rental) {
		this.rental = rental;
	}

	public ImageEntity getImage() {
		return image;
	}

	public void setImage(ImageEntity image) {
		this.image = image;
	}

}
