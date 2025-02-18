package com.example.demo.login;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.member.MemberDto;

@Mapper
public interface LoginMapper {
	public String loginOk(MemberDto mdto);
	public String getUserid(MemberDto mdto);
	public String getUserPwd(MemberDto mdto);
	//public String chgPwd(String userid, String pwd);
	public String chgPwd(String userid, String pwd, String oldPwd);
	public void addCart(String userid, String pcode, String su);
	public void cookieUpdate(String su,String userid, String pcode);
	public boolean cartIs(String userid, String pcode);
}
