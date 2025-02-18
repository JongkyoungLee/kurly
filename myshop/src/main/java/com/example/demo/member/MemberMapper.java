package com.example.demo.member;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.dto.GumaeDto;
import com.example.demo.dto.ProductDto;
import com.example.demo.dto.ReviewDto;

@Mapper
public interface MemberMapper {
	public Integer useridCheck(String userid);
	public void memberOk(MemberDto mdto);
	public ArrayList<ProductDto> cartView(String userid);
	public void cartDel(String userid);
	public void chgSu(String id, String num);
	public ProductDto getProduct(String pcode);
	public ArrayList<ProductDto> jjimList(String userid);
	public void jjimDel(String id);
	public void addCart(String userid, String pcode);
	public String getCartSu(String userid);
	public Boolean isCart(String userid, String pcode);
	public void upCart(String userid, String pcode);
	public ArrayList<HashMap>buyList(String userid);
	public void chgState(GumaeDto gdto);
	public void reviewOk(ReviewDto rdto);
	public void chgReview(int id);
	public Double avgStar(String pcode);
	public void chgStar(double avg, String pcode);
	public void upReview(String pcode);
	public void reviewDelete(String id);
	public void chgStar2(double avg, String pcode);
	public void setReview(String id);
	public ReviewDto getReview(String id);
	public void reviewUpdateOk(ReviewDto rdto);
}
