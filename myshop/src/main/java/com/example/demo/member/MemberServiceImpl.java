package com.example.demo.member;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.util.WebUtils;

import com.example.demo.MyUtil;
import com.example.demo.dto.GumaeDto;
import com.example.demo.dto.ProductDto;
import com.example.demo.dto.ReviewDto;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Service
@Qualifier("ms")
public class MemberServiceImpl implements MemberService {

	@Autowired
	private MemberMapper mapper;
	
	@Override
	public String member() {
		
		return "/member/member";
	}
	
	@Override
	public String useridCheck(HttpServletRequest request) {
		String userid = request.getParameter("userid");
		
		return mapper.useridCheck(userid).toString();
	}
	
	@Override
	public String memberOk(MemberDto mdto) {
		// 아이디 중복체크를 다시 한다.
		Integer n =	mapper.useridCheck(mdto.getUserid());
		if(n == 0) {
			return "redirect:/login/login";
		}else {
			mapper.memberOk(mdto);			
			return "redirect:/member/member?err=1";
		}
	}
	
	@Override
	public String cartView(HttpServletRequest request, Model model, HttpSession session) {
		int size;
		ArrayList<ProductDto> plist = null;
		if(session.getAttribute("userid") == null) {
			// 쿠키변수를 읽어온다 상품코드-수량
			Cookie cookie = WebUtils.getCookie(request, "cart");
			if(cookie != null && !cookie.getValue().equals("")) {
			 String[] pcodes = cookie.getValue().split("/");
			 
			
			 plist = new ArrayList<ProductDto>();
			 DecimalFormat df = new DecimalFormat("#,###");
			 for(int i = 0; i < pcodes.length; i++) {
				    String pcode = pcodes[i].substring(0,12); // 상풍코드
				    ProductDto pdto = mapper.getProduct(pcode);
				    
					MyUtil.getBaeday(pdto);
					int price = pdto.getPrice();
					int halin = pdto.getHalin();
					int juk = pdto.getJuk();
					  
					double halinPrice = price-(price * halin / 100.0);
					double jukPrice = price * juk / 100.0;
					
					pdto.setHalinPriceStr(df.format(halinPrice));
					pdto.setJukPriceStr(df.format(jukPrice));
					
					// 계산된 할인금액을 price필드에 저장해서 뷰에 전달
					pdto.setPrice((int)halinPrice);
					
					// ArrayList의 pdto에 있는 wchk값이 1인 pdto의 갯수와
					// ArrayList의 size가 같으면 모두 선택
					
					int su = Integer.parseInt(pcodes[i].substring(13));
					pdto.setSu(su);
					
					// wchk를 모두 1
					pdto.setWchk(1);
					
					plist.add(pdto);
					
			 }
			 model.addAttribute("plist", plist);
			
			}else {
				
			}
		}else {
			// cart테이블의 정보를 전달하기
			
			String userid = session.getAttribute("userid").toString();
			plist = mapper.cartView(userid);
			
			DecimalFormat df = new DecimalFormat("#,###");
			// 1.baeday를 baedayStr로 저장 
			int wchkNum = 0;
			for(int i = 0; i < plist.size(); i++) {
				ProductDto pdto = plist.get(i);
				
				MyUtil.getBaeday(pdto);
				int price = pdto.getPrice();
				int halin = pdto.getHalin();
				int juk = pdto.getJuk();
				  
				double halinPrice = price-(price * halin / 100.0);
				double jukPrice = price * juk / 100.0;
				
				pdto.setHalinPriceStr(df.format(halinPrice));
				pdto.setJukPriceStr(df.format(jukPrice));
				
				// 계산된 할인금액을 price필드에 저장해서 뷰에 전달
				pdto.setPrice((int)halinPrice);
				/*
				String[] wday = pdto.getWriteday().split("-");
				int year = Integer.parseInt(wday[0]);
				int month = Integer.parseInt(wday[1]);
				int day = Integer.parseInt(wday[2]);
			
				LocalDate cartDay = LocalDate.of(year, month , day); // cart테이블의 날짜
				LocalDate today = LocalDate.now();
			
				if(today.isEqual(cartDay)) {
					pdto.setWchk(1);
				}else {
					pdto.setWchk(0);
				}
				*/
			
				if(pdto.getWchk() == 1 ) {
					wchkNum++;
				}
			}
			int allChk = 0;
			if(wchkNum == plist.size()) {
				allChk=1;
			}
			model.addAttribute("allChk", allChk);
			model.addAttribute("plist",plist);
			int height=660;
			size = 0;
			if(plist != null) {
				size = plist.size();
			}
			if(size > 4) {
			    int n = size-4;
				n = n * 115;
				height = height + n;
			}
			
			model.addAttribute("height", height);
			model.addAttribute("today", LocalDate.now().toString());
		}
		
		
		return "member/cartView";
	}
	
	@Override
	public String cartDel(HttpServletRequest request, HttpSession session, HttpServletResponse response) {
		
		if(session.getAttribute("userid")== null) {
			String pcodes = request.getParameter("ids"); // 상품코드1
			
			Cookie cookie = WebUtils.getCookie(request, "cart");
			if(cookie != null && !cookie.getValue().equals("")) {
				String[] carts = cookie.getValue().split("/");// 쿠키 변수에 있는 값 => 상품코드1-수량/상품코드2-수량
				String[] pcodeDel = pcodes.split(",");
				for(int i = 0; i < carts.length; i++) {
					for(int j = 0; j < pcodeDel.length; j++) {
						if(carts[i].indexOf(pcodeDel[j]) != -1) {
							carts[i]="";
						}
						
					}
				}
				// 배열의 상품코드를 문자열로 변경
				String newCart= "";
				for(int i = 0; i < carts.length; i++) {
					if(!(carts[i].equals(""))) {
						newCart=newCart + carts[i]+"/";
					}
				}
				
				cookie = new Cookie("cart", newCart);
				cookie.setMaxAge(60*60*24);
				cookie.setPath("/");
				
				response.addCookie(cookie);
				
			}
			
		}else {
			String ids = request.getParameter("ids");
			
			String[] imsi = ids.split(",");
			
			for(int i = 0; i < imsi.length; i++) {
				mapper.cartDel(imsi[i]);
			}
		}
		
		return "redirect:/member/cartView";
	}
	
	@Override
	public String chgSu(HttpServletRequest request, HttpSession session, HttpServletResponse response) {
		String id = request.getParameter("id");
		String num = request.getParameter("num");
		String pcode = request.getParameter("pcode");
		
		if(session.getAttribute("userid") == null) {
		 Cookie cookie = WebUtils.getCookie(request, "cart");
		 if(cookie != null && !cookie.getValue().equals("")) {
			String[] carts = cookie.getValue().split("/");
			for(int i = 0; i < carts.length; i++) {
				if(carts[i].indexOf(pcode) != -1) {
					// 상품코드-
				 carts[i] = pcode + "-" + num;
				}
			}
			String newCart = "";
			for(int i = 0; i < carts.length; i++) {
				newCart=newCart+carts[i]+"/";
			}
			cookie = new Cookie("cart", newCart);
			cookie.setMaxAge(60);
			cookie.setPath("/");
			response.addCookie(cookie);
			
		 }
		 return "0";
		}else {
			mapper.chgSu(id, num);
				
			return "0";
		}
	}
	
	@Override
	public String jjimList(HttpSession session, Model model) {
		if(session.getAttribute("userid") == null) {
			
			return "redirect:/login/login";
		}else {
			String userid = session.getAttribute("userid").toString();
			ArrayList<ProductDto> plist = mapper.jjimList(userid);
			model.addAttribute("plist",plist);
			return "/member/jjimList";
		}
		
		
	}
	@Override
	public String jjimDel(HttpServletRequest request, HttpSession session) {
		if(session.getAttribute("userid") == null) {
			
			return "redirect:/login/login";
		}else {
			String ids = request.getParameter("ids");
			mapper.jjimDel(ids);
			return "redirect:/member/jjimList";
		}
		
		
	}
	@Override
	public String choiceDel(HttpServletRequest request, HttpSession session) {
		if(session.getAttribute("userid") == null) {
			
			return "redirect:/login/login";
			
		}else {
			String ids = request.getParameter("ids");
			String[] imsi = ids.split(",");
			for(int i = 0; i < imsi.length; i++) {
				mapper.jjimDel(imsi[i]);
			}
			
			return "redirect:/member/jjimList";
		}
		
	}
	@Override
	public String addCart(HttpServletRequest request, HttpSession session) {
		if(session.getAttribute("userid") == null) {
			
			return "redirect:/login/login";
		}else {
			String userid = session.getAttribute("userid").toString();
			String pcode = request.getParameter("pcode");
			
			mapper.addCart(userid, pcode);
			return "redirect:/member/jjimList";
		}
	}
	
	@Override
	public String addCart2(HttpServletRequest request, HttpSession session) {
		if(session.getAttribute("userid") == null) {
			
			return "-1";
		}else {
			String userid = session.getAttribute("userid").toString();
			String pcode = request.getParameter("pcode");
			
			if(mapper.isCart(userid, pcode)) {
				mapper.upCart(userid, pcode);
			}else {
				mapper.addCart(userid, pcode);				
			}
			
			
			return mapper.getCartSu(userid);
		}
		
	}
	@Override
	public String buyList(HttpSession session, Model model) {
		
		if(session.getAttribute("userid") == null) {
			
			return "redirect:/login/login";
		}else {
			String userid = session.getAttribute("userid").toString();
		    ArrayList<HashMap> mapAll = mapper.buyList(userid);
			for(int i = 0; i < mapAll.size(); i++) {
				HashMap map = mapAll.get(i);
				int state = Integer.parseInt(map.get("state").toString());
				int price = Integer.parseInt(map.get("price").toString());
				String stateStr = MyUtil.getState(state);
				String priceStr = MyUtil.comma(price);
				map.put("stateStr", stateStr);
				map.put("priceStr", priceStr);
			}
		    
		    
		    model.addAttribute("mapkey",mapAll);
			return "/member/buyList";
		}
		
		
	}
	@Override
	public String chgState(HttpSession session, GumaeDto gdto) {
		if(session.getAttribute("userid") == null) {
			
			return "redirect:/login/login";
		}else {
			
			mapper.chgState(gdto);
			
			return "redirect:/member/buyList";
		}
		
	}
	
	@Override
	public String review(ReviewDto rdto, HttpSession session, Model model) {
		model.addAttribute("gid", rdto.getId()); // gumae테이블의 id
		model.addAttribute("pcode", rdto.getPcode());
		
		return "/member/review";
	}
	@Override
	public String reviewOk(ReviewDto rdto, HttpSession session) {
		// review테이블에 저장
		String userid = session.getAttribute("userid").toString();
		rdto.setUserid(userid);
		rdto.setGid(rdto.getId());
		mapper.reviewOk(rdto);
		
		// 상품평을 추가한 구매내용에 상품평을 등록했다는것을 저장
		// gumae.review필드의 값을 변경 => 1로
		mapper.chgReview(rdto.getId());
		
		// 상품당 별점의 평균은 product테이블에서 평균 계산하기때문에
		// 상품평을 추가한 상품의 평균별점을 계산하여 product.star에 저장시킨다.
		// review.star(동일한 상품) 평균계산
		double avg = mapper.avgStar(rdto.getPcode()); // 현재 상품의 별점
		
		mapper.chgStar(avg, rdto.getPcode()); // product.star필드
		mapper.upReview(rdto.getPcode());
		return "redirect:/member/buyList";
	}
	@Override
	public String reviewDelete(HttpServletRequest request, HttpSession session) {
		if(session.getAttribute("userid") == null) {
			return "redirect:/login/login";
		}else {
			String id = request.getParameter("id");
			String pcode = request.getParameter("pcode");
			String gid = request.getParameter("gid");
			mapper.reviewDelete(id);
			
			// 상품평을 삭제
			// 1. 별점을 재계산, product.review=>-1로
			// 상품평이 하나도 없을 때 null 발생
			
			double avg = mapper.avgStar(pcode); 
			mapper.chgStar2(avg, pcode);
			// 2. 상품평 버튼 다시 활성화 gumae.review => 0으로 바꿈
			mapper.setReview(gid);
			return "redirect:/product/productContent?pcode="+pcode;
		}
		
	}
	@Override
	public String reviewUpdate(HttpServletRequest request, HttpSession session, Model model) {
		if(session.getAttribute("userid") == null) {
			return "redirect:/login/login";
		}else {
			
			String id = request.getParameter("id");
			//String pcode = request.getParameter("pcode");
			// pcode는 review테이블에서 읽어올 수 있으므로 없어도 된다.
			
		    ReviewDto rdto = mapper.getReview(id);
		    model.addAttribute("rdto",rdto);
		    return "/member/reviewUpdate";
	}
		
		
	
	}
@Override
public String reviewUpdateOk(ReviewDto rdto, HttpSession session) {
	if(session.getAttribute("userid")== null) {
		return "redirect:/login/login";
	}else {
		// 1. review테이블 수정
		mapper.reviewUpdateOk(rdto);
		// 2. 해당상품의 별점을 계산 => product.star에 수정
		double avg = mapper.avgStar(rdto.getPcode());
		mapper.chgStar(avg, rdto.getPcode());
		return "redirect:/product/productContent?pocde="+rdto.getPcode();
	}

}
}
