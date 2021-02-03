package com.rentit.project.pojo.response;

import java.util.List;

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

	public String getAccessToken() {
		return token;
	}

	public void setAccessToken(String accessToken) {
		this.token = accessToken;
	}

	public String getTokenType() {
		return type;
	}

	public void setTokenType(String tokenType) {
		this.type = tokenType;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public List<String> getRoles() {
		return roles;
	}
}
