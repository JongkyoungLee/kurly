package com.example.demo.dto;

import lombok.Data;

@Data
public class QnaDto {
	private int id, ref, qna;
	private String userid, content, writeday, ptitle;
}
