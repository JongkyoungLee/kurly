package com.example.demo;

import java.lang.annotation.Repeatable;
import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import com.example.demo.dto.CalDto;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class EtcController {
    // 이것저것 연습할때 사용할 클래스
	private CalMapper mapper;
	@GetMapping("/etc/thisEx")
	public String thisEx()
	{
		return "/etc/thisEx";
	}
	
	@GetMapping("/etc/selectJung")
	public String selectJung()
	{
		return "/etc/selectJung";
	}
	
	@GetMapping("/etc/numberString")
	public String numberString()
	{
		return "/etc/numberString";
	}
	
	@GetMapping("/etc/menu2")
	public String menu2()
	{
		return "/etc/menu2";
	}
	@GetMapping("/etc/menu")
	public String menu()
	{
		return "/etc/menu";
	}
	@GetMapping("/etc/menu3")
	public String menu3()
	{
		return "/etc/menu3";
	}
	
	@GetMapping("/etc/jsCal")
	public String jsCal()
	{
		return "/etc/jsCal";
	}
	@GetMapping("/etc/getMyCal")
	public @ResponseBody ArrayList<CalDto> getHoliday(HttpServletRequest request){
		String y = request.getParameter("y");
		String m = request.getParameter("m");
		String day = y +"-" +m;
		
		if(m.length() == 1) {
			m="0"+m;
			
			
		}
		
		return mapper.getMyCal(day);
	}
	@GetMapping("/etc/jsCal2")
	public String jsCal2() {
		
		return "etc/jsCal2";
	}
	@GetMapping("/etc/star")
	public String star(Model model) {
		double star = 3.89;
		
		int ystar = 0, hstar = 0, gstar = 0;
		
		ystar = (int)star;
		double imsi = star - ystar;
		if(imsi >= 0.8) {
			ystar++;
		}else if(imsi <= 0.2 && imsi > 0.8) {
			hstar = 1;
		}
		
		model.addAttribute("ystar", ystar);
		model.addAttribute("hstar", hstar);
		model.addAttribute("gstar", gstar);
		
		return "/etc/star";
	}
	@GetMapping("/etc/chkbox")
	public String chkbox() {
		
		return "etc/chkbox";
	}

	@RequestMapping("/etc/juso")
	public String juso() {
		
		return "etc/juso";
	}
	
	@RequestMapping("/etc/form")
	public String form() {
		return "etc/form";
	}
	
	@RequestMapping("/etc/formOk")
	public void formOk(HttpServletRequest request) {
		String[] pcodes = request.getParameterValues("pcode");
		String[] suss = request.getParameterValues("su");
		
		
	}
	
	@RequestMapping("/etc/form2")
	public void from2() {
		
	
	}
}











