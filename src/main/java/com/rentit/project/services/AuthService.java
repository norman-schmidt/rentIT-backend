package com.rentit.project.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.rentit.project.jwt.JwtUtils;
import com.rentit.project.models.ERole;
import com.rentit.project.models.Role;
import com.rentit.project.models.UserEntity;
import com.rentit.project.pojo.request.LoginRequest;
import com.rentit.project.pojo.request.SignupRequest;
import com.rentit.project.pojo.response.JwtResponse;
import com.rentit.project.pojo.response.MessageResponse;
import com.rentit.project.repositories.RoleRepository;
import com.rentit.project.repositories.UserRepository;

@Service
public class AuthService {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	private MailService mailService;

	public ResponseEntity<?> authenticateUser(LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getEmail(),
				userDetails.getLastname(), userDetails.getFirstname(), roles, userDetails.getStreet(),
				userDetails.getHausNumber(), userDetails.getPlz(), userDetails.getOrt(), userDetails.getBirthday(),
				userDetails.getRental(), userDetails.getImage()));

	}

	public ResponseEntity<?> registerUser(SignupRequest signUpRequest) {
		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
		}

		// Create new user's account
		UserEntity user = new UserEntity(signUpRequest.getEmail(), encoder.encode(signUpRequest.getPassword()),
				signUpRequest.getLastname(), signUpRequest.getFirstname(), signUpRequest.getStreet(),
				signUpRequest.getHausNumber(), signUpRequest.getPlz(), signUpRequest.getOrt(),
				signUpRequest.getBirthday(), signUpRequest.getRental(), signUpRequest.getImage()

		);

		Set<String> strRoles = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();

		if (strRoles == null) {
			Role userRole = roleRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(adminRole);

					break;
				case "mod":
					Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(modRole);

					break;
				default:
					Role userRole = roleRepository.findByName(ERole.ROLE_USER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(userRole);
				}
			});
		}

		user.setRoles(roles);
		userRepository.save(user);

		String text = "Congratulations!!! \n\n\n Dear " + signUpRequest.getLastname()
				+ ",\n\n\n the registration was a success! \n\n\n you can click hier to signIn \n\n https://rentit24.tech/#/login  \n\n\n\n Kind Regards\n\n\nBest Team JEE 2021";
		String subject = "Successfully registred!!!";
		mailService.sendMail(text, user, subject);

		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}

}
