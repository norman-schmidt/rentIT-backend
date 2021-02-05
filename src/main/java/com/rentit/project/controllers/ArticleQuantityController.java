package com.rentit.project.controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rentit.project.models.ArticleQuantityEntity;
import com.rentit.project.models.InvoiceEntity;
import com.rentit.project.models.RentalEntity;
import com.rentit.project.models.UserEntity;
import com.rentit.project.pojo.query.CustomPojoReturn;
import com.rentit.project.pojo.response.MessageResponse;
import com.rentit.project.services.ArticleQuantityService;
import com.rentit.project.services.ArticleService;
import com.rentit.project.services.InvoiceService;
import com.rentit.project.services.MailService;
import com.rentit.project.services.RentalService;
import com.rentit.project.services.UserService;

@RestController
@RequestMapping("/api/quantities")
@CrossOrigin(origins = "*")
public class ArticleQuantityController {

	@Autowired
	private RentalService rentalService;

	@Autowired
	private ArticleService articleService;

	@Autowired
	private InvoiceService invoiceService;

	@Autowired
	private ArticleQuantityService quantityService;

	@Autowired
	private UserService userService;

	@Autowired
	private MailService mailService;

	@GetMapping("")
	public List<ArticleQuantityEntity> getAllQuantity() {
		return quantityService.getAllArticleQuantitys();
	}

	@GetMapping("{id}")
	public ArticleQuantityEntity getQuantityById(@PathVariable long id) {
		return quantityService.getArticleQuantity(id);
	}

	@GetMapping("listRental")
	public List<CustomPojoReturn> getListRentalUser(@RequestHeader(value = "Authorization") String authHeader) {
		UserEntity user = userService.getUserFromToken(authHeader);
		return quantityService.getListRentalUser(user.getUserId());
	}

	@PostMapping("")
	public ResponseEntity<MessageResponse> addQuantity(@RequestBody List<ArticleQuantityEntity> quantityEntity,
			@RequestHeader(value = "Authorization") String authHeader) {
		StringBuilder list = new StringBuilder();
		UserEntity user = userService.getUserFromToken(authHeader);

		double subTotal = 0.0; // subTotal for quantityEntity
		double totalPrice = 0.0; // totalPrice for rental

		// invoiceEntity
		InvoiceEntity invoiceEntity = new InvoiceEntity();
		invoiceEntity.setInvoiceDate(LocalDateTime.now());
		// invoiceEntity.setInvoiceNumber(Integer.parseInt(UUID.randomUUID().toString()));
		invoiceService.addInvoice(invoiceEntity);

		// rentalEntity
		RentalEntity rentalEntity = new RentalEntity();
		rentalEntity.setInvoice(invoiceEntity);
		rentalEntity.setRentDate(LocalDateTime.now());
		rentalEntity.setUsers(user);

		// hole ArticleId von quantityEntity
		// hole article and price
		// Rechnung totalPrice und subTotal
		for (int i = 0; i < quantityEntity.size(); i++) {
			// Difference Date-Days
			// long diffDay = ChronoUnit.DAYS.between(LocalDateTime.now(),
			// quantityEntity.get(i).getReturnDate());
			subTotal = quantityEntity.get(i).getQuantity()
					* articleService.getArticle(quantityEntity.get(i).getArticle().getArticleId()).getPrice();
			quantityEntity.get(i).setSubTotal(subTotal);

			// rabatt
			// if (diffDay >= 7) {
			// subTotal -= subTotal * 0.1;
			// } else if (diffDay >= 7 && diffDay <= 14) {
			// subTotal -= subTotal * 0.15;
			// } else if (diffDay > 14) {
			// subTotal -= subTotal * 0.2;
			// }

			// subTotal
			quantityEntity.get(i).setSubTotal(subTotal);

			// total
			totalPrice += subTotal;

			list.append("Article Nr " + i + " : " + quantityEntity.get(i).getArticle().getName());
		}

		rentalEntity.setTotalPrice(totalPrice);
		rentalService.addRental(rentalEntity);

		// update quantityEntity
		for (int i = 0; i < quantityEntity.size(); i++) {
			quantityEntity.get(i).setRental(rentalEntity);
			quantityEntity.get(i).setReturned(false);
			quantityService.addArticleQuantity(quantityEntity.get(i));
		}
		String text = "Congratulations!!! \n\n\n Dear " + user.getLastname()
				+ ",\n\n\n Articles was rented successfully !!! \n\n\n Following articles: \n " + list.toString()
				+ " \n\n https://rentit24.tech/  \n\n\n\n Kind Regards\n\n\nBest Team JEE 2021";
		String subject = "Successfully rented!!!";
		mailService.sendMail(text, user, subject);

		return ResponseEntity.ok().body(new MessageResponse("Successfully Added"));
	}

	@PostMapping("return")
	public ResponseEntity<MessageResponse> returnArticle(@RequestBody Map<String, ArrayList<Long>> data,
			@RequestHeader(value = "Authorization") String authHeader) {

		UserEntity user = userService.getUserFromToken(authHeader);
		Boolean toLate = false;
		double price = 0.0, actuPrice = 0.0;

		// invoiceEntity
		InvoiceEntity invoiceEntity = new InvoiceEntity();
		invoiceEntity.setInvoiceDate(LocalDateTime.now());
		// invoiceEntity.setInvoiceNumber(Integer.parseInt(UUID.randomUUID().toString()));
		invoiceService.addInvoice(invoiceEntity);

		ArrayList<Long> ids = data.get("ids");
		System.out.println(ids);
		for (int i = 0; i < ids.size(); i++) {

			ArticleQuantityEntity articleQuantityEntity = quantityService.getArticleQuantity(ids.get(i));

			articleQuantityEntity.setReturned(true);

			articleQuantityEntity.setReturnedDate(LocalDateTime.now());

			toLate = LocalDateTime.now().isBefore(articleQuantityEntity.getReturnDate());

			if (!toLate) {
				price = articleQuantityEntity.getSubTotal() * 1.3;
				actuPrice = price - articleQuantityEntity.getSubTotal();
				String text = "Warning!!! \n\n\n Dear " + user.getLastname()
						+ ",\n\n\n Articles was to late returned !!! \n\n\n You must pay in addition: \n " + actuPrice
						+ " \n\n https://rentit24.tech/  \n\n\n\n Kind Regards\n\n\nBest Team JEE 2021";
				String subject = "Successfully returned!!!";
				mailService.sendMail(text, user, subject);
			} else {
				String text = "Congratulations!!! \n\n\n Dear " + user.getLastname()
						+ ",\n\n\n Articles was returned successfully !!! \n\n\n "
						+ " \n\n https://rentit24.tech/  \n\n\n\n Kind Regards\n\n\nBest Team JEE 2021";
				String subject = "Successfully returned!!!";
				mailService.sendMail(text, user, subject);
			}

			articleQuantityEntity.setSubTotal(price);
			quantityService.addArticleQuantity(articleQuantityEntity);
		}

		return ResponseEntity.ok().body(new MessageResponse("Successfully returned"));
	}

	@PutMapping("{id}")
	public ArticleQuantityEntity updateQuantity(@RequestBody ArticleQuantityEntity quantityEntity,
			@PathVariable long id) {
		ArticleQuantityEntity _quantityEntity = quantityService.getArticleQuantity(id);
		_quantityEntity.setQuantity(quantityEntity.getQuantity());
		_quantityEntity.setReturnedDate(quantityEntity.getReturnedDate());
		_quantityEntity.setArticle(quantityEntity.getArticle());
		_quantityEntity.setReturnDate(quantityEntity.getReturnDate());
		_quantityEntity.setReturned(quantityEntity.isReturned());
		_quantityEntity.setRental(rentalService.updateRental(_quantityEntity.getRental()));
		return quantityService.updateArticleQuantities(_quantityEntity);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<MessageResponse> removeQuantity(@PathVariable Long id) {
		quantityService.deleteArticleQuantity(id);
		return ResponseEntity.ok().body(new MessageResponse("Successfully deleted"));
	}

}
