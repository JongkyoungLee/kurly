package com.example.demo.member;

import org.springframework.ui.Model;

import com.example.demo.dto.GumaeDto;
import com.example.demo.dto.ReviewDto;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public interface MemberService {
	public String member();
	public String useridCheck(HttpServletRequest request);
	public String memberOk(MemberDto mdto);
	public String cartView(HttpServletRequest request, Model model, HttpSession session);
	public String cartDel(HttpServletRequest request, HttpSession session, HttpServletResponse response);
	public String chgSu(HttpServletRequest request, HttpSession session,HttpServletResponse response);
	public String jjimList(HttpSession session, Model model);
	public String jjimDel(HttpServletRequest request, HttpSession session);
	public String choiceDel(HttpServletRequest request, HttpSession session);
	public String addCart(HttpServletRequest request, HttpSession session);
	public String addCart2(HttpServletRequest request, HttpSession session);
	public String buyList(HttpSession session, Model model);
	public String chgState(HttpSession session, GumaeDto gdto);
	public String review(ReviewDto rdto, HttpSession session, Model model);
	public String reviewOk(ReviewDto rdto, HttpSession session);
	public String reviewDelete(HttpServletRequest request, HttpSession session);
	public String reviewUpdate(HttpServletRequest request, HttpSession session, Model model);
	public String reviewUpdateOk(ReviewDto rdto, HttpSession session);
}
