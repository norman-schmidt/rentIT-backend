package com.rentit.project.pojo.response;

import java.util.Date;
import java.util.List;

import com.rentit.project.models.ImageEntity;
import com.rentit.project.models.RentalEntity;

import lombok.Data;

@Data
public class JwtResponse {

	private String token;
	private String type = "Bearer";
	private long id;
	private String email;
	private List<String> roles;
	private String lastname;
	private String firstname;

	private String street;
	private String hausNumber;
	private int plz;
	private String ort;
	private Date birthday;
	private List<RentalEntity> rental;
	private ImageEntity image;

	public JwtResponse(String accessToken, long id, String email, String lastname, String firstname, List<String> roles,
			String street, String hausNumber, int plz, String ort, Date birthday, List<RentalEntity> rental,
			ImageEntity image) {
		this.token = accessToken;
		this.id = id;
		this.email = email;
		this.roles = roles;
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
