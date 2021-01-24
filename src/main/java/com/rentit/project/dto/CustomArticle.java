package com.rentit.project.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomArticle implements Serializable {//

	/**
	 * 
	 */
	private static final long serialVersionUID = -315681232546235083L;

	private String name;
	private String description;
	private int stockLevel;
	private double price;
	private String imageLink;

	// public CustomArticle(String name, String description, int stockLevel, double
	// price,String imagLink){}
}
