package com.rentit.project.pojos;



import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomPojoReturn {
	private long article_quantityId;
	private long articelId;
	private String name;
	private String description;
	private String imageLink;
	private int quantity;
	private LocalDateTime rent_date;
	private LocalDateTime return_date;
}
