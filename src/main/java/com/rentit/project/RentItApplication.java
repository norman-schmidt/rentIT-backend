package com.rentit.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableScheduling
public class RentItApplication {
	
// public class RentItApplication implements CommandLineRunner { -> pour executer les repositories au demarage

//	@Autowired
//	private ArticleRepository articleRepository;
//
//	@Autowired
//	private ImageRepository imageRepository;
//
//	@Autowired
//	private InvoiceRepository invoiceRepository;
//	
//	@Autowired
//	private UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(RentItApplication.class, args);
	}

//	@Override
//	public void run(String... args) throws Exception {
//		ArticleEntity art = new ArticleEntity(1, "IPhone x", "DEG542DF", "v12", 12, 900, "Description", null , null, null, null);
//		articleRepository.save(art);
//
//		ImageEntity img = new ImageEntity(1, "image link 1", "image type", null, null);
//		imageRepository.save(img);
//
//		Date date = new Date();
//		InvoiceEntity invoice = new InvoiceEntity(1, 2, date, null);
//		invoiceRepository.save(invoice);
//		
//		UserEntity user = new UserEntity(1, "test@test.com", "testPassword", "Jack", "Franck", "Magdeburger Straße 10", date, null, null);
//		userRepository.save(user);
//		
//	}

}
