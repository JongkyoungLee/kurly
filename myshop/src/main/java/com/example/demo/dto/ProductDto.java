package com.example.demo.dto;

import lombok.Data;

@Data
public class ProductDto {
	
	private int id, price, halin, su, baeprice, baeday, juk, pansu, review, sales;
	private String pcode, pimg, dimg, title, writeday, salesDay;
	private double star;
	
	private String baedayStr;
	private int ystar, hstar, gstar;
	
	private String priceStr, halinPriceStr, jukPriceStr, baePriceStr;
	private int wchk;
	
}
