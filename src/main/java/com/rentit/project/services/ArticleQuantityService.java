package com.rentit.project.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.rentit.project.models.ArticleQuantityEntity;
import com.rentit.project.models.UserEntity;
import com.rentit.project.pojo.query.CustomPojoReturn;
import com.rentit.project.repositories.ArticleQuantityRepository;

@Service
public class ArticleQuantityService {

	@Autowired
	private MailService mailService;

	@Autowired
	private ArticleQuantityRepository articleQuantityRepository;

	public ArticleQuantityEntity addArticleQuantity(ArticleQuantityEntity articleQuantity) {
		return articleQuantityRepository.save(articleQuantity);
	}

	public ArticleQuantityEntity getArticleQuantity(Long id) {
		return articleQuantityRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}

	public List<ArticleQuantityEntity> getAllArticleQuantitys() {
		return articleQuantityRepository.findAll();
	}

	public void deleteArticleQuantity(Long id) {
		ArticleQuantityEntity articleQuantity = getArticleQuantity(id);
		articleQuantityRepository.delete(articleQuantity);
	}

	public ArticleQuantityEntity updateArticleQuantities(ArticleQuantityEntity list) {
		return articleQuantityRepository.save(list);
	}

	public List<ArticleQuantityEntity> updateArticleQuantities(List<ArticleQuantityEntity> list) {
		return articleQuantityRepository.saveAll(list);
	}

	@Transactional
	public List<CustomPojoReturn> getListRentalUser(Long userId) {
		return articleQuantityRepository.getListRentalUser(userId);
	}

	//call the function eceryDay at 08AM 
	@Scheduled(cron = "0 8 * * ?")
	public void checkAllArticlesQuantities() {
		LocalDateTime date = LocalDateTime.now();

		List<ArticleQuantityEntity> quantities = articleQuantityRepository.findAll();
		for (int i = 0; i < quantities.size(); i++) {
			UserEntity user = quantities.get(i).getRental().getUsers();
			if (date.equals(quantities.get(i).getReturnDate().minusDays(1))) {
				String text = "reminder!!! \n\n\n Dear " + user.getLastname()
						+ ",\n\n\n Please pay attention! you must return the rented items tomorrow !!! \n\n\n"
						+ " \n\n https://rentit24.tech/  \n\n\n\n Kind Regards\n\n\nBest Team JEE 2021";
				String subject = "Reminder!!!";
				mailService.sendMail(text, user, subject);
			}
		}
	}

}
