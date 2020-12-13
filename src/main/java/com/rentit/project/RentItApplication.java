package com.rentit.project;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.rentit.project.models.ArticleEntity;
import com.rentit.project.models.ImageEntity;
import com.rentit.project.models.InvoiceEntity;
import com.rentit.project.models.UserEntity;
import com.rentit.project.repositories.ArticleRepository;
import com.rentit.project.repositories.ImageRepository;
import com.rentit.project.repositories.InvoiceRepository;
import com.rentit.project.repositories.UserRepository;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
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
//		// TODO Auto-generated method stub
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
