package com.example.demo;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.dto.CalDto;

import jakarta.servlet.http.HttpServletRequest;

@Mapper
public interface CalMapper {
	public ArrayList<CalDto> getMyCal(String day);
}
