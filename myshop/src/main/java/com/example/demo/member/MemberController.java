package com.example.demo.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.dto.GumaeDto;
import com.example.demo.dto.ReviewDto;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class MemberController {
	
	@Autowired
	@Qualifier("ms")
	private MemberService service;
	
	@GetMapping("/member/member")
	public String member() {
		
		return service.member();
	}
	
	@GetMapping("/member/useridCheck")
	public @ResponseBody String useridCheck(HttpServletRequest request) {
		
		return service.useridCheck(request);
	}
	
	@PostMapping("/member/memberOk")
	public String memberOk(MemberDto mdto) {
	
		return service.memberOk(mdto);
	}
	
	@GetMapping("/member/cartView")
	public String cartView(HttpServletRequest request, Model model, HttpSession session) {
		
		return service.cartView(request, model, session);
	}
	
	@GetMapping("/member/cartDel")
	public String cartDel(HttpServletRequest request, HttpSession session, HttpServletResponse response) {
		
		return service.cartDel(request, session, response);
	}
	
	@GetMapping("/member/chgSu")
	public @ResponseBody String chgSu(HttpServletRequest request, HttpSession session, HttpServletResponse response) {
		
		return service.chgSu(request, session, response);
	}
	
	@GetMapping("/member/jjimList")
	public String jjimList(HttpSession session, Model model) {
		
		return service.jjimList(session, model);
	}
	@GetMapping("/member/jjimDel")
	public String jjimDel(HttpServletRequest request, HttpSession session) {
		
		return service.jjimDel(request, session);
	}
	@GetMapping("/member/choiceDel")
	public String choiceDel(HttpServletRequest request, HttpSession session) {
		
		return service.choiceDel(request,session);
	}
	@GetMapping("/member/addCart")
	public String addCart(HttpServletRequest request, HttpSession session) {
		
		return service.addCart(request, session);
	}
	@GetMapping("/member/addCart2")
	public @ResponseBody String addCart2(HttpServletRequest request, HttpSession session) {
		
		return service.addCart2(request, session);
	}
	@GetMapping("/member/buyList")
	public String buyList(HttpSession session, Model model) {
		return service.buyList(session, model);
	}
	
	@GetMapping("/member/chgState")
	public String chgState(HttpSession session, GumaeDto gdto) {
		
		return service.chgState(session, gdto);
	}
	
	@GetMapping("/member/review")
	public String review(ReviewDto rdto, HttpSession session, Model model) {
	
		return service.review(rdto, session, model);
	}
	
	@PostMapping("/member/reviewOk")
	public String reviewOk(ReviewDto rdto, HttpSession session) {
		
		return service.reviewOk(rdto, session);
		
	}
	@GetMapping("/member/reviewDelete")
	public String reviewDelete(HttpServletRequest request, HttpSession session) {
		
		return service.reviewDelete(request, session);
	}
	
	@GetMapping("/member/reviewUpdate")
	public String reviewUpdate(HttpServletRequest request, HttpSession session, Model model) {
		
		return service.reviewUpdate(request, session, model);
	}
	@PostMapping("/member/reviewUpdateOk")
	public String reviewUpdateOk(ReviewDto rdto, HttpSession session) {
		
		return service.reviewUpdateOk(rdto, session);
	}

}
