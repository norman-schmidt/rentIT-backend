package com.rentit.project.pojo.response;

import java.util.List;

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

	public JwtResponse(String accessToken, long id, String email, String lastname, String firstname,
			List<String> roles) {
		this.token = accessToken;
		this.id = id;
		this.email = email;
		this.roles = roles;
		this.lastname = lastname;
		this.firstname = firstname;
	}

}
