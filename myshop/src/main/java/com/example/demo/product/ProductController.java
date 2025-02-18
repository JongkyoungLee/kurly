package com.example.demo.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import com.example.demo.dto.BaesongDto;
import com.example.demo.dto.CartDto;
import com.example.demo.dto.GumaeDto;
import com.example.demo.dto.QnaDto;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class ProductController {

	@Qualifier("ps")
	@Autowired
	private ProductService ps;
	
	
	@GetMapping("/product/productList")
	public String productList(HttpServletRequest request, Model model) {
		
		return ps.productList(request, model);
		
	}
	
	@GetMapping("/product/productContent")
	public String productContent(HttpServletRequest request, Model model, HttpSession session) {
		
		return ps.productContent(request, model, session);
	}
	
	@GetMapping("/product/jjimOk")
	public @ResponseBody String jjimOk(HttpServletRequest request, HttpSession session) {
		
		return ps.jjimOk(request, session);
	}
	@GetMapping("/product/jjimClear")
	public @ResponseBody String jjimClear(HttpServletRequest request, HttpSession session) {
		
		return ps.jjimClear(request, session);
	}
	@GetMapping("/product/addProduct")
	public @ResponseBody String addProduct(CartDto cdto, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		
		return ps.addProduct(cdto, session, request, response);
	}
	@GetMapping("/product/cookieView")
	public @ResponseBody String cookieView(HttpServletRequest request) {
		Cookie cookie = WebUtils.getCookie(request, "cart");
		String cook = "";
		if(cookie != null) {
			cook = cookie.getValue();
		}
		return cook;
	}
	
	@GetMapping("/product/cookieDel")
	public @ResponseBody String cookieDel(HttpServletRequest request, HttpServletResponse response) {
		Cookie cookie = WebUtils.getCookie(request, "cart");
		
		cookie = new Cookie("cart", "");
		cookie.setMaxAge(0);
		cookie.setPath("/");
		
		response.addCookie(cookie);
		return cookie.getValue();
	}
	
	@RequestMapping("/product/productGumae")
	public String productGumae(HttpSession session, HttpServletRequest request, Model model) {
		
		return ps.productGumae(session,request, model);
	}
	
	@GetMapping("/product/baesongWrite")
	public String baesongWrite() {
		
		return ps.baesongWrite();
	}
	
	@PostMapping("/product/baesongWriteOk")
	public String baesongWriteOk(BaesongDto bdto, HttpSession session) {
		
		return ps.baesongWriteOk(bdto,session);
	}
	
	@GetMapping("/product/baesongUpdate")
	public String baesongUpdate(HttpServletRequest request, HttpSession session, Model model) {
		
		return ps.baesongUpdate(request, session, model);
	}
	
	@PostMapping("/product/updateOk")
	public String baesongUpdateOk(HttpServletRequest request,BaesongDto bdto) {
		
		return ps.updateOk(request,bdto);
	}
	
	@GetMapping("/product/baesongDelete")
	public String baesongDelete(HttpServletRequest request, HttpSession session) {
		
		return ps.baesongDelete(request, session);
	}
	
	@GetMapping("/product/baesongList")
	public String baesongList(HttpSession session, Model model) {
		
		return ps.baesongList(session, model);
	}
	
	@PostMapping("/product/gumaeOk")
	public String gumaeOk(GumaeDto gdto, HttpSession session) {
		
		return ps.gumaeOk(gdto, session);
	}
	
	@GetMapping("/product/buyComplete")
	public String buyComplete(HttpServletRequest request, HttpSession session, Model model) {
		
		return ps.buyComplete(request, session, model);
		
	}
	
	@PostMapping("/product/qnaWriteOk")
	public String qnaWriteOk(QnaDto qdto, HttpServletRequest request, HttpSession session) {
	
		return ps.qnaWriteOk(qdto, request, session);
	}
	
	
	
	

}
	
