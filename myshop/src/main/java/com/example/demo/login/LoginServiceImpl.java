package com.example.demo.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.util.WebUtils;

import com.example.demo.MailSend;
import com.example.demo.MyUtil;
import com.example.demo.member.MemberDto;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Service
@Qualifier("ls")
public class LoginServiceImpl implements LoginService {
	@Autowired
	private LoginMapper mapper;
	
	@Override
	public String login(Model model, HttpServletRequest request) {
		model.addAttribute("err", request.getParameter("err"));
		model.addAttribute("pcode", request.getParameter("pcode"));
		model.addAttribute("su", request.getParameter("su"));
		model.addAttribute("cart", request.getParameter("cart"));
		return "/login/login";
	}
	
	@Override
	public String loginOk(MemberDto mdto, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		String name = mapper.loginOk(mdto);
		if(name == null) {
			
			return "redirect:/login/login?err=1";
		}else {
			// 세션변수 할당 후 메인페이지로 이동
			session.setAttribute("userid", mdto.getUserid());
			session.setAttribute("name", name);
			//session.setMaxInactiveInterval(600);
			// 찜하다가 왔을경우 productContent 이동
			
			
			Cookie cookie = WebUtils.getCookie(request, "cart");
			
			if(cookie != null && !cookie.getValue().equals("")) {
				String cart = cookie.getValue(); // 상품코드1-수량/상품코드2-수량/상품코드3-수량
				String[] carts = cart.split("/");
				
				for(int i = 0; i < carts.length; i++) {
					System.out.println(carts[i]);
					String[] imsi = carts[i].split("-"); // imsi[0]=상품코드 [1]=수량
					
				  if(mapper.cartIs(mdto.getUserid(), imsi[0])) {
					  
					  mapper.cookieUpdate(imsi[1],mdto.getUserid(), imsi[0]);
				  }else {
					  
					  mapper.addCart(mdto.getUserid(), imsi[0], imsi[1]);
				  }
				}
				
				// 추가된 후 쿠키변수는 삭제
				cookie = new Cookie("cart", "");
				cookie.setMaxAge(0);
				cookie.setPath("/");
				
			}
			String pcode = request.getParameter("pcode");
			String su = request.getParameter("su");
			if(pcode.equals("")) {
				if(request.getParameter("cart") != null) {
					
					return "redirect:/login/login";	
				}else {
					return "redirect:/main/main";
				}
			}else {
				  if(su == null) {
					return "redirect:/product/productContent?pcode="+pcode;
				  }else {
					return "redirect:/product/productGumae?pcode="+pcode+"&su="+su;
				  }
				}
				
			}
			
	
		}
	

	@Override
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/main/main";
	}
	
	@Override
	public String getUserid(MemberDto mdto) {
		
		String userid = mapper.getUserid(mdto);
		System.out.println(userid);
		return userid;
	}
	
	@Override
	public String getUserPwd(MemberDto mdto) throws Exception {
		
		String userpwd = mapper.getUserPwd(mdto);
		
		// 이메일로 비밀번호를 전송하면 된다.
		if(userpwd != null) {
		/*	
			MailSend mailsend = MailSend.getInstance();
			String title = mdto.getUserid() + "님의 비밀번호를 보내드립니다.";
			String content = "당신의 비밀번호는" + userpwd + "입니다.";
	 		mailsend.setEmail(mdto.getEmail(), title, content, "");
	 	*/	
			// 6자리의 임의의 비밀번호를 구해서 메세지로 보내기
			// 
			String imsiPwd = MyUtil.getNewPwd();
			
			mapper.chgPwd(mdto.getUserid(),imsiPwd,userpwd);
			
			// 기존의 비밀본호를 새로 만든 비밀번호로 교체하기
	 		return "임시 비밀번호 :" + imsiPwd;
		
		}else {
			return "정보가 일치하지 않습니다.";
		}
	}
}
