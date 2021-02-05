package com.rentit.project.services;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rentit.project.models.ImageEntity;
import com.rentit.project.models.RentalEntity;
import com.rentit.project.models.UserEntity;

import lombok.Data;

@Data
public class UserDetailsImpl implements UserDetails {

	private static final long serialVersionUID = 1L;

	private long id;

	private String username;

	private String email;

	@JsonIgnore
	private String password;

	private String lastname;

	private String firstname;

	private String street;
	private String hausNumber;
	private int plz;
	private String ort;
	private Date birthday;
	private List<RentalEntity> rental;
	private ImageEntity image;

	private Collection<? extends GrantedAuthority> authorities;

	public UserDetailsImpl(long id, String username, String email, String lastname, String firstname, String password,
			Collection<? extends GrantedAuthority> authorities, String street, String hausNumber, int plz, String ort,
			Date birthday, List<RentalEntity> rental, ImageEntity image) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.authorities = authorities;
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

	public static UserDetailsImpl build(UserEntity user) {
		List<GrantedAuthority> authorities = user.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getName().name())).collect(Collectors.toList());

		return new UserDetailsImpl(user.getUserId(), user.getUsername(), user.getEmail(), user.getLastname(),
				user.getFirstname(), user.getPassword(), authorities, user.getStreet(), user.getHausNumber(),
				user.getPlz(), user.getOrt(), user.getBirthday(), user.getRental(), user.getImage());
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
