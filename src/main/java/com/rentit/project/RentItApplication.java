package com.rentit.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableScheduling
public class RentItApplication {

// public class RentItApplication implements CommandLineRunner { 

//	@Autowired
//	private RoleRepository roleRepository;

	public static void main(String[] args) {
		SpringApplication.run(RentItApplication.class, args);
	}

//	@Override
//	public void run(String... args) throws Exception {
//		Role role1 = new Role();
//		role1.setId(1);
//		role1.setName(ERole.ROLE_ADMIN);
//		roleRepository.save(role1);
//
//		Role role2 = new Role();
//		role2.setId(2);
//		role2.setName(ERole.ROLE_MODERATOR);
//		roleRepository.save(role2);
//
//		Role role3 = new Role();
//		role3.setId(3);
//		role3.setName(ERole.ROLE_USER);
//		roleRepository.save(role3);
//
//	}

}
