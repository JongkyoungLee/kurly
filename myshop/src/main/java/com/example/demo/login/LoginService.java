package com.example.demo.login;

import org.springframework.ui.Model;

import com.example.demo.member.MemberDto;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public interface LoginService {
	public String login(Model model, HttpServletRequest request);
	public String loginOk(MemberDto mdto, HttpSession session, HttpServletRequest request, HttpServletResponse response);
	public String logout(HttpSession session);
	public String getUserid(MemberDto mdto);
	public String getUserPwd(MemberDto mdto) throws Exception;
	
}
