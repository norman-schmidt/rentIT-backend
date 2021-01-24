package com.rentit.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomArticle {

	private String name;
	private String description;
	private int stockLevel;
	private double price;
	private String imageLink;
}