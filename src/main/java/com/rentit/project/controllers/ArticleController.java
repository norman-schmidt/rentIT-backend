package com.rentit.project.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rentit.project.models.ArticleEntity;
import com.rentit.project.services.ArticleService;

@RestController
@RequestMapping("/api/articles/")
public class ArticleController {

	@Autowired
	private ArticleService articleService;

	@GetMapping("all")
	public List<ArticleEntity> getAllArticle() {
		return articleService.getAllArticles();
	}

	@GetMapping("{id}")
	public ArticleEntity getArticleById(@PathVariable long id) {
		return articleService.getArticle(id);
	}

	@PostMapping("add")
	public ArticleEntity addArticle(@RequestBody ArticleEntity articleEntity) {
		return articleService.addArticle(articleEntity);
	}

	@PutMapping("add/{id}")
	public ArticleEntity addArticleWithId(@RequestBody ArticleEntity articleEntity, @PathVariable long id) {
		ArticleEntity _articleEntity = articleService.getArticle(id);
		
		_articleEntity.setName("articleEntity.getName()");
		
		//return articleService.addArticle(_articleEntity);
	}
	
	/*
	 * @PutMapping("employee/{id}/{id_v}") public ResponseEntity<EmployeeEntity>
	 * updateEmployee(@PathVariable Long id, @PathVariable Long id_v, @RequestBody
	 * EmployeeEntity employeeToUpload) { EmployeeEntity employee =
	 * employeeService.getEmployeeById(id); VehicleEntity vehicle =
	 * vehicleService.getVehicleById(id_v);
	 * 
	 * vehicles.add(vehicle);
	 * 
	 * employee.setFirstName(employeeToUpload.getFirstName());
	 * employee.setLastName(employeeToUpload.getLastName());
	 * employee.setEmailId(employeeToUpload.getEmailId());
	 * employee.setVehicles(vehicles);
	 * 
	 * 
	 * EmployeeEntity uploadedEmployee = employeeService.createEmp(employee);
	 * 
	 * 
	 * 
	 * return ResponseEntity.ok(uploadedEmployee); }
	 */

}
