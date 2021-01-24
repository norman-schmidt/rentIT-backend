package com.rentit.project.pojo.category;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Category {

	private String name;
	private String description;
	private int stockLevel;
	private double price;
	private String imageLink;

}
