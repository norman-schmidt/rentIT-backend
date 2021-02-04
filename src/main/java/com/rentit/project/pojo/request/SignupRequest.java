package com.rentit.project.pojo.request;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.rentit.project.models.ImageEntity;
import com.rentit.project.models.RentalEntity;

import lombok.Data;

@Data
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

}
