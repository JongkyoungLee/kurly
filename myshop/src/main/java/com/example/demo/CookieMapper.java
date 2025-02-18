package com.example.demo;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.dto.DaeDto;
import com.example.demo.dto.JungDto;
import com.example.demo.dto.SoDto;

@Mapper
public interface CookieMapper {
	public ArrayList<DaeDto> getDae();
	public ArrayList<JungDto> getJung(String daecode);
	public ArrayList<SoDto> getSo(String daejung);
	public String getCartNum(String userid);
}
