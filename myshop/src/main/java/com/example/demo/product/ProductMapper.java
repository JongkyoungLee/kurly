package com.example.demo.product;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.dto.BaesongDto;
import com.example.demo.dto.CartDto;
import com.example.demo.dto.GumaeDto;
import com.example.demo.dto.ProductDto;
import com.example.demo.dto.QnaDto;
import com.example.demo.dto.ReviewDto;
import com.example.demo.member.MemberDto;

@Mapper
public interface ProductMapper {
	public ArrayList<ProductDto> productList(int index, String pcode, String orders);
	public int getChong(String pcode);
	public String getDae(String code);
	public String getJung(String dae, String jung);
	public String getSo(String daejung, String so);
	public ProductDto productContent(String pcode);
	public void jjimOk(String pcode, String userid);
	public int isJjim(String pcode, String userid);
	public void jjimClear(String pcode, String userid);
	public void addProduct(CartDto cdto);
	public int cartChk(CartDto cdto);
	public void updateSu(CartDto cdto);
	public String getCartNum(String userid);
	public MemberDto getMember(String userid);
	public BaesongDto getBaeSong(String userid);
	public void baesongWriteOk(BaesongDto bdto);
	public ArrayList<BaesongDto> baesongList(String userid);
	public void setZero(String userid);
	public int isBaesong(String userid); 
	public int getNumber(String numuncode);
	public void gumaeOk(GumaeDto gdto);
	public void setJuk(int useJuk, String userid);
	public void cartDel(String userid, String pcode);
	public void deleteAddress(String userid ,int id);
	public BaesongDto getAddress(String userid, int id);
	public void baesongUpdateOk(BaesongDto bdto);
	public ArrayList<GumaeDto> getGumae(String jumuncode);
	public BaesongDto getBaesong2(String id);
	public ArrayList<ReviewDto> getReview(String pcode);
	public void qnaWriteOk(QnaDto qdto);
}
