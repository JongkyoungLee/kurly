package com.example.demo.dto;

import lombok.Data;

@Data
public class ReviewDto {
	private int id, star, gid;
	private String userid, pcode, title, content, writeday;
	
	private String chUserid;
}
