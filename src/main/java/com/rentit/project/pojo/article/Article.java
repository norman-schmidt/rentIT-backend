package com.rentit.project.pojo.article;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Article {

	private String name;
	private String description;
	private int stockLevel;
	private double price;
	private String imageLink;
}
