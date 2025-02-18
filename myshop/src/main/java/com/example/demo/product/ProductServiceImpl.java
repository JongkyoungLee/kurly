package com.example.demo.product;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.util.WebUtils;

import com.example.demo.MyUtil;
import com.example.demo.dto.BaesongDto;
import com.example.demo.dto.CartDto;
import com.example.demo.dto.GumaeDto;
import com.example.demo.dto.ProductDto;
import com.example.demo.dto.QnaDto;
import com.example.demo.dto.ReviewDto;
import com.example.demo.member.MemberDto;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Service
@Qualifier("ps")
public class ProductServiceImpl implements ProductService {
	@Autowired
	private ProductMapper mapper;
	
	@Override
	public String productList(HttpServletRequest request, Model model) {
		// 대, 중, 소 코드에 해당되는 상품을 읽어서 전달하기
		String pcode = request.getParameter("pcode");
		
		
		// 요청 페이지를 이용하여 시작되는 페이지의 인덱스를 구하기
		int page = 1;
		if(request.getParameter("page") != null) {
			page = Integer.parseInt(request.getParameter("page"));
		}
		int index = (page - 1) * 10;
		int pstart, pend, chong;
		
		pstart = page / 10;
		if(page % 10 == 0) {
			pstart--;
		}
		pstart = (pstart * 10) + 1;
		
		pend = pstart + 9;
		chong = mapper.getChong(pcode);
		if(pend > chong) {
			pend = chong;
		}
		
		model.addAttribute("pstart", pstart);
		model.addAttribute("pend", pend);
		model.addAttribute("chong", chong);
		model.addAttribute("page", page);
		model.addAttribute("pcode", pcode);
		
		// 정렬형태를 처리하기
		// 가격높은 순, 가격낮은 순, 판매량순, 최신순
		String orderStr = "";
		int order = 1;
		if(request.getParameter("order") != null) {
			order = Integer.parseInt(request.getParameter("order"));
		}
		
		switch(order) {
			case 1:orderStr = "pansu desc"; break;
			case 2:orderStr = "price desc"; break;
			case 3:orderStr = "price asc"; break;
			case 4:orderStr = "star desc"; break;
			case 5:orderStr = "writeday desc"; break;
			
			default : orderStr = "pansu desc"; 
		}
		
		model.addAttribute("order", order);
		
		// pcode를 이용하여 Home - 대분류 - 중분류 - 소분류를 뷰에 전달
		String menuList = "";
		if(pcode.length() == 3) {
			// Home - 대분류
			 String dae = pcode.substring(1);
			 String daeName = mapper.getDae(dae);
			 menuList = "Home - " + daeName;
		}else if(pcode.length() == 5) {
			// Home - 대분류 - 중분류
			String dae = pcode.substring(1,3);
			String jung = pcode.substring(3);
			
			String daeName = mapper.getDae(dae);
			String jungName = mapper.getJung(dae, jung);
			
			menuList = "Home - " + daeName + " - " + jungName;
		}else {
			// Home - 대분류 - 중분류 - 소분류
			String dae = pcode.substring(1,3);
			String jung = pcode.substring(3,5);
			String so = pcode.substring(5);
			
			String daeName = mapper.getDae(dae);
			String jungName = mapper.getJung(dae, jung);
			String daejung = dae + jung;
			String soName = mapper.getSo(daejung, so);
			
			menuList = "Home - " + daeName + " - " + jungName + " - " + soName;
		}
		
		model.addAttribute("menuList",menuList);
		ArrayList<ProductDto> plist = mapper.productList(index ,pcode, orderStr);
		for(int i = 0; i < plist.size(); i++ ) {
		  ProductDto pdto = plist.get(i);
		/*  
		
		  int[] stars = MyUtil.getStar(pdto.getStar());
		  pdto.setYstar(stars[0]);
		  pdto.setHstar(stars[1]);
		  pdto.setGstar(stars[2]);
		 
		  */
		  MyUtil.getBaeday(pdto);
		  MyUtil.getStar2(pdto.getStar(), pdto);
		  // 상품금액, 할인금액, 적립금을 계산 후 소수점=>x 3자리마다 ,
		  int price = pdto.getPrice();
		  int halin = pdto.getHalin();
		  int juk = pdto.getJuk();
		  
		  double halinPrice = price-(price * halin / 100.0);
		  double jukPrice = halinPrice * juk / 100.0;
		  
		  DecimalFormat df = new DecimalFormat("#,###");
		  
		  pdto.setHalinPriceStr(df.format(halinPrice));
		  pdto.setJukPriceStr(df.format(jukPrice));
		}
		
		model.addAttribute("plist",plist);
		return "/product/productList";
	}
	@Override
	public String productContent(HttpServletRequest request, Model model, HttpSession session) {
		
		String pcode = request.getParameter("pcode");
		int ok = 0;
		if(session.getAttribute("userid") != null) {
			String userid = session.getAttribute("userid").toString();
			
			ok = mapper.isJjim(pcode, userid);
		}
		model.addAttribute("ok", ok);
		ProductDto pdto = mapper.productContent(pcode);
		
		MyUtil.getStar2(pdto.getStar(), pdto);
		
		  int price = pdto.getPrice();
		  int halin = pdto.getHalin();
		  int juk = pdto.getJuk();
		  
		  double halinPrice = price-(price * halin / 100.0);
		  double jukPrice = halinPrice * juk / 100.0;
		  
		  DecimalFormat df = new DecimalFormat("#,###");
		  String priceStr = df.format(price);
		  
		  pdto.setPriceStr(priceStr);
		  pdto.setHalinPriceStr(df.format(halinPrice));
		  pdto.setJukPriceStr(df.format(jukPrice));

		  MyUtil.getBaeday(pdto);
		  
		  model.addAttribute("pdto",pdto);
		  
		  // 상품평에 출력할 내용 => review테이블에서 가져오기
		  ArrayList<ReviewDto> rlist = mapper.getReview(pcode);
		  model.addAttribute("rlist",rlist);
		  return "/product/productContent";
	}
	@Override
	public String jjimOk(HttpServletRequest request, HttpSession session) {
		  
		  if(session.getAttribute("userid")== null) {
			  return "0";
			  
		  }else {
			  String pcode = request.getParameter("pcode");
			  String userid = session.getAttribute("userid").toString();
			  
			  mapper.jjimOk(pcode, userid);
			  return "1";
		  }
	}
	@Override
	public String jjimClear(HttpServletRequest request, HttpSession session) {
		if(session.getAttribute("userid") == null) {
			
			return "0";
		}else {
			String pcode = request.getParameter("pcode");
			String userid = session.getAttribute("userid").toString();
			
			mapper.jjimClear(pcode, userid);
			return "1";
		}
	
	}
	
	@Override
	public String addProduct(CartDto cdto, HttpSession session
			,HttpServletRequest request, HttpServletResponse response) {
		if(session.getAttribute("userid") ==  null) {
			// 쿠키 변수에 상품코드와 수량을 저장한다.
			
			// 새로 추가되는 상품코드
			String newCode = cdto.getPcode() + "-" + cdto.getSu() + "/";
			// WebUtils.getCookie는 여러개의 쿠키 중 원하는 쿠키 하나만 가져온다.
			Cookie cookie = WebUtils.getCookie(request, "cart");
			//											cart라는 쿠키 변수를 가져오기
			if(cookie == null || cookie.getValue().equals("")) { // 쿠키가 없으면 새로운 쿠키를 생성한다.
				cookie = new Cookie("cart", newCode); // cart는 cart 변수에 newCode를 저장
				cookie.setMaxAge(60*60*24);
				cookie.setPath("/");
				System.out.println("새로 생성 : " + cookie.getValue());
				response.addCookie(cookie);// 쿠키 저장방법
				}else { // 기존 쿠키 값에 새로운 값을 추가한다.
					String cartCookieValue = cookie.getValue(); // 기존 쿠키 값
					
					String[] carts = cartCookieValue.split("/");
					
					int chk = 0;
					for(int i = 0; i < carts.length; i++) {
						if(carts[i].indexOf(cdto.getPcode()) != -1) { // cart[i]에 값이 있다면
						  int hap = Integer.parseInt(carts[i].substring(13)) + cdto.getSu();
						  System.out.println(hap + " " + carts[i].substring(13));
						  carts[i] = carts[i].substring(0, 13)+hap;
						  
						  chk = 1;
						}
						
					}
					// 배열에 있는 상품을 String으로 변경 "/"을 구분자
					String cartNew = "";
					for(int i = 0; i < carts.length; i++) {
						cartNew = cartNew + carts[i]+"/"; 
					}
					if(chk == 1) {
						cartCookieValue = cartNew;
					}else {
						cartCookieValue = cartNew + newCode; // 기존 쿠키 값 + 새로 추가되는 코드 값
						
					}
					
					cookie = new Cookie("cart", cartCookieValue); // 쿠키에 쿠키 값을 생성
					cookie.setMaxAge(60*60*24);
					cookie.setPath("/");
					
					System.out.println("추가 생성 : " + cookie.getValue());
					response.addCookie(cookie);
			}
			
			
		
		}else {
			String userid = session.getAttribute("userid").toString();
			cdto.setUserid(userid);
			int num = mapper.cartChk(cdto);
			System.out.println(num);
			if(mapper.cartChk(cdto) == 1) {
				mapper.updateSu(cdto);
			}else {
				mapper.addProduct(cdto);
				
			}
			
		}
		//return "1";	
		// 세션여부에 따라 장바구니의 갯수를 구해서 리턴해주기
		if(session.getAttribute("userid") == null) {
			Cookie cookie = WebUtils.getCookie(request, "cart");
			if(cookie == null || cookie.getValue().equals("")) {
				return "0";
			}else {
				return cookie.getValue().split("/").length + "";
			}
		}else {
			String userid = session.getAttribute("userid").toString();
			return mapper.getCartNum(userid);
		}
	}
	@Override
	public String productGumae(HttpSession session, HttpServletRequest request, Model model) {
		String userid = session.getAttribute("userid").toString();
		String pcode = request.getParameter("pcode");
		String su = request.getParameter("su");
		// 구매자 정보
		if(session.getAttribute("userid")== null) {
			return "redirect:/login/login?pcode="+pcode+"&su="+su;
		}
		else {
			MemberDto mdto = mapper.getMember(userid);
			
			model.addAttribute("mdto",mdto);
			// 배송지 정보
			BaesongDto bdto = mapper.getBaeSong(userid); 
			if(bdto != null) {
				String reqStr = null;
				switch(bdto.getReq()) {
					case 0 : reqStr = "문 앞"; break;
					case 1 : reqStr = "직접 받고 부재 시 문 앞"; break;
					case 2 : reqStr = "경비실"; break;
					case 3 : reqStr = "택배함"; break;
					case 4 : reqStr = "기타"; break;
					
					default : reqStr = "";
				}
				bdto.setReqStr(reqStr);
			}
			model.addAttribute("bdto",bdto);
			// 상품 정보
			String[] pcodes = pcode.split(",");
			String[] sus = su.split(",");
			String imsiPcode = "";
			String imsiSu = "";
			ArrayList<ProductDto> plist = new ArrayList<ProductDto>();
			for(int i = 0; i < pcodes.length; i++) {
				ProductDto pdto = mapper.productContent(pcodes[i]);
				MyUtil.getBaeday(pdto);
				// pdto의 su 변수는 재고수량
				pdto.setSu(Integer.parseInt(sus[i])); // 구매수량
				DecimalFormat df = new DecimalFormat("#,###");
				imsiPcode = imsiPcode + pcodes[i] +"/";
				imsiSu = imsiSu + sus[i] + "/";
				// 총상품금액, 적립금, 
				int price = pdto.getPrice(); // 할인 전 상품금액
				int halin = pdto.getHalin(); // 할인 율 
				price = price-(price * halin/100);
				pdto.setPriceStr(df.format(price));
				pdto.setPrice(price);
				// 보유하고 있는 적립금 MemberDto
				// 총결제금액 => 할인금액 계산 된 값을 pdto.price에 재저장
				//int cPrice = price + pdto.getBaeprice();
				
				plist.add(pdto);
			}
			model.addAttribute("imsiPcode", imsiPcode);
			model.addAttribute("imsiSu",imsiSu);			
			
			model.addAttribute("plist", plist);
			return "/product/productGumae";
		}
	
	}
	
	@Override
	public String baesongWrite() {
		// TODO Auto-generated method stub
		return "/product/baesongWrite";
	}
	
	@Override
	public String baesongWriteOk(BaesongDto bdto, HttpSession session) {
		// 배송지 주소를 baesong테이블에 저장
		
		bdto.setUserid(session.getAttribute("userid").toString());
		
		// 현재 입력된 주소를 기본주소로 한다면 기존의 기본주소를 0으로 변경
		if(bdto.getGibon() == 1) {
			mapper.setZero(session.getAttribute("userid").toString());
		}
		if(bdto.getGibon() == 0 && mapper.isBaesong(session.getAttribute("userid").toString()) == 0) {
			bdto.setGibon(1);
		}
		mapper.baesongWriteOk(bdto);
		return "redirect:/product/baesongList";
	}
	
	@Override
	public String baesongList(HttpSession session, Model model) {
		
	    String userid = session.getAttribute("userid").toString();
		ArrayList<BaesongDto> blist = mapper.baesongList(userid);
			
		for(int i = 0; i < blist.size(); i++) {
			BaesongDto bdto = blist.get(i);
			String reqStr = null;
				switch(bdto.getReq()) {
					case 0 : reqStr = "문 앞"; break;
					case 1 : reqStr = "직접 받고 부재 시 문 앞"; break;
					case 2 : reqStr = "경비실"; break;
					case 3 : reqStr = "택배함"; break;
					case 4 : reqStr = "기타"; break;
					
					default : reqStr = "";
				}
				bdto.setReqStr(reqStr);
			
		}
		
	    model.addAttribute("blist",blist);
		return "/product/baesongList";
	}
	
	@Override
	public String gumaeOk(GumaeDto gdto, HttpSession session) 
	{
		if(session.getAttribute("userid")==null)
		{
			return "redirect:/login/login";
		}
		else
		{
			String userid=session.getAttribute("userid").toString();
			gdto.setUserid(userid);
			
			// 주문코드 만들기  => j20250211 + 001
			String today=LocalDate.now().toString();
			today=today.replace("-", "");
			String jumuncode="j"+today; // j20250211
			
			int num=mapper.getNumber(jumuncode);
			jumuncode=jumuncode+String.format("%03d", num);
			gdto.setJumuncode(jumuncode);
			
			// pcode와 su의 값을 배열로 만들고 하나씩 저장하기
			String[] pcodes = gdto.getPcode().split("/");
			String[] sus = gdto.getSus().split("/");
			
			for(int i = 0; i < pcodes.length; i++) {
				// 배열에 각 요소에 있는 pcode, su으로 setter
				gdto.setPcode(pcodes[i]);
				gdto.setSu(Integer.parseInt(sus[i]));
				mapper.gumaeOk(gdto);
				
				mapper.cartDel(userid, pcodes[i]);
			}
			
			// member테이블의 juk필드의 값에서 사용된 적립금 값을 빼고 저장
			mapper.setJuk(gdto.getUseJuk(), userid);
			// 구매 버튼을 누르고 보낼 수 있는 값은 jumuncode jumuncode를 넘겨준다.
			// gumaeOk에서 insert된 jumuncode를 이용한다.
			return "redirect:/product/buyComplete?jumuncode="+jumuncode;
		}
	
	}
	
	@Override
	public String baesongDelete(HttpServletRequest request, HttpSession session) {
		if(session.getAttribute("userid").toString() == null) {
			return "redirect:/login/login";
		}else {
			int baeId =Integer.parseInt(request.getParameter("id"));
			String userid = request.getParameter("userid");
			
			mapper.deleteAddress(userid, baeId);
			
			return "redirect:/product/baesongList";
		}
		
		
		
	
	}
	@Override
	public String baesongUpdate(HttpServletRequest request, HttpSession session, Model model) {
		String userid = request.getParameter("userid");
		int id =Integer.parseInt(request.getParameter("id"));
		if(session.getAttribute(userid) == null) {
			return "/etc/sessionOut";
		}else {
			
			BaesongDto bdto = mapper.getAddress(userid, id);
			System.out.println(id);			
			model.addAttribute("bdto",bdto);
			
			return "/product/baesongUpdate";
		}
			
		
		
	}
	
	@Override
	public String updateOk(HttpServletRequest request, BaesongDto bdto) {
		mapper.baesongUpdateOk(bdto);
		return "redirect:/product/baesongList?id="+bdto.getId() +"&userid="+bdto.getUserid();
	}
	@Override
	public String buyComplete(HttpServletRequest request, HttpSession session,Model model) {
		// 방금 주문한 상품의 주문코드를 이용하여 상품의 정보, 구매내역을 사용자에게 보여준다.
		// 상품정보 : 배송예정일, 수량, 상품그림, 제목, 할인적용된 금액 
		//배송지정보 : 받는사람, 우편번호, 주소, 배송요청사항
		// 구매가격 : 모든 상품금액, 모든 배송비, 사용적립금 => 총 결제 금액 
		// 3개의 테이블에 접근하기 때문에 innner join오로 접근이 가능하지만 
		// 따로 접근하는 것도 가능하다.
		String jumuncode = request.getParameter("jumuncode");
		// gumae 테이블 내용 가져오기
		ArrayList<GumaeDto> glist = mapper.getGumae(jumuncode);
		int halinPriceAll =0,baePriceAll=0;
		int useJuk = 0; // 사용 적립금은 누적시키면 안되기 때문에 0으로 초기화를 안시킴
		ArrayList<ProductDto> plist = new ArrayList<ProductDto>();
		
		for(int i = 0; i < glist.size(); i++) {
			// gdto에 
			GumaeDto gdto = glist.get(i);
			ProductDto pdto = mapper.productContent(gdto.getPcode());
			
			// 배송예정일
			MyUtil.getBaeday(pdto);
			
			String reqStr = null;
			
			// 상품 수량
			pdto.setSu(gdto.getSu());
			// 상품그림, 제목은 pdto에 들어있다.
			// 할인 적용된 금액 : 상품금액-(상품금액 - 할인율/100)
			int halinPrice = pdto.getPrice()-(pdto.getPrice() * pdto.getHalin() / 100);
			pdto.setPrice(halinPrice);
			
			// 상품의 합계금액, 배송비합계금액, 사용된 적립금 => 따로 뷰에 전달
			// 하나 씩만 출력되면 되기 때문에
			halinPriceAll = halinPriceAll + halinPrice;
			baePriceAll = baePriceAll + pdto.getBaeprice();
			// 사용 적립금은 하나만 불러오면 된다. db에 사용적립금이 있기 때문에
			useJuk = gdto.getUseJuk();
			
			plist.add(pdto);
		}
		
		model.addAttribute("plist", plist);
		
		GumaeDto gdto = glist.get(0);
		BaesongDto bdto = mapper.getBaesong2(gdto.getBaeId()+"");
		String reqStr ="";
		switch(bdto.getReq()) {
		case 0 : reqStr = "문 앞"; break;
		case 1 : reqStr = "직접 받고 부재 시 문 앞"; break;
		case 2 : reqStr = "경비실"; break;
		case 3 : reqStr = "택배함"; break;
		case 4 : reqStr = "기타"; break;
		
		default : reqStr = "";
	}
		bdto.setReqStr(reqStr);
		gdto.getBaeId();
		model.addAttribute("bdto",bdto);
		model.addAttribute("halinPrice",MyUtil.comma(halinPriceAll) );
		model.addAttribute("baePrice", MyUtil.comma(baePriceAll) );
		model.addAttribute("useJuk", MyUtil.comma(useJuk) );
		model.addAttribute("sumPrice", MyUtil.comma(halinPriceAll + baePriceAll - useJuk));
		
		return "/product/buyComplete";
	}
	
	@Override
	public String qnaWriteOk(QnaDto qdto, HttpServletRequest request, HttpSession session) {
		String userid = session.getAttribute("userid").toString();
		String pcode = request.getParameter("pcode");
		qdto.setUserid(userid);
		
		mapper.qnaWriteOk(qdto);
				
		
		
		return "redirect:/product/productContent?pcode="+pcode;
	}
}

