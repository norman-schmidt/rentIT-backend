package com.rentit.project.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.rentit.project.jwt.JwtUtils;
import com.rentit.project.models.ImageEntity;
import com.rentit.project.models.RentalEntity;
import com.rentit.project.models.UserEntity;
import com.rentit.project.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	private RentalService rentalService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ImageService imageService;

	public UserEntity addUser(UserEntity user) {
		return userRepository.save(user);
	}

	public UserEntity getUser(Long id) {
		return userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}

	public UserEntity findUserByEmail(String email) {
		return userRepository.findByEmail(email).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}

	public List<UserEntity> getAllUsers() {
		return userRepository.findAll();
	}

	public void deleteUser(Long id) {
		UserEntity user = getUser(id);
		userRepository.delete(user);
	}

	public UserEntity updateUser(UserEntity userEntity, long id) {

		UserEntity _userEntity = getUser(id);

		_userEntity.setEmail(userEntity.getEmail());
		_userEntity.setLastname(userEntity.getLastname());
		_userEntity.setPassword(userEntity.getPassword());
		_userEntity.setFirstname(userEntity.getFirstname());
		_userEntity.setStreet(userEntity.getStreet());
		_userEntity.setHausNumber(userEntity.getHausNumber());
		_userEntity.setPlz(userEntity.getPlz());
		_userEntity.setOrt(userEntity.getOrt());
		_userEntity.setBirthday(userEntity.getBirthday());
		_userEntity.setImage(imageService.updateImage(_userEntity.getImage(), _userEntity.getImage().getImageId()));

		return userRepository.save(userEntity);
	}

	public UserEntity getUserFromToken(String authHeader) {

		String token = authHeader.substring(7, authHeader.length());
		String email = null;
		if (jwtUtils.validateJwtToken(token)) {
			email = jwtUtils.getUserNameFromJwtToken(token);
		}
		UserEntity user = findUserByEmail(email);
		return user;
	}

	public UserEntity addUserRental(long id_user, long id_rental) {
		UserEntity user = getUser(id_user);
		RentalEntity rental = rentalService.getRental(id_rental);
		user.getRental().add(rental);
		rental.setUsers(user);
		updateUser(user, id_user);
		rentalService.updateRental(rental, id_rental);
		return user;
	}

	public UserEntity removeUserRental(long id_user, long id_rental) {
		UserEntity user = getUser(id_user);
		RentalEntity rental = rentalService.getRental(id_rental);
		user.getRental().remove(rental);
		rental.setUsers(user);
		updateUser(user, id_user);
		rentalService.updateRental(rental, id_rental);
		return user;
	}

	public UserEntity addUserImage(long id_user, long id_image) {
		UserEntity user = getUser(id_user);
		ImageEntity image = imageService.getImage(id_image);
		user.setImage(image);
		image.setUser(user);
		updateUser(user, id_user);
		imageService.updateImage(image, id_image);
		return user;
	}

}
