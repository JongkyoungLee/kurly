package com.example.demo;

import java.text.DecimalFormat;
import java.time.LocalDate;

import com.example.demo.dto.ProductDto;

public class MyUtil {

	public static String getNewPwd(){
		String newNum = "";
		for(int i = 0; i < 6; i++) {
			int rand = (int)(Math.random()*90);
		
			rand += 33;
			newNum += (char)rand;
		}
		
		return newNum;
		
		
	}
	
	// 별점을 실수로 받은 뒤 노란별, 반별, 회색별의 갯수를 구하기
	public static int[] getStar(double star) {
		
		int ystar = 0, gstar = 0, hstar = 0;
		
		  ystar = (int)star;
		  double imsi = star - ystar;
		  
		  if(imsi >= 0.8) {
			 ystar++;
		  }else if(imsi >= 0.2 && imsi < 0.8) {
			 hstar = 1;
		  }
		  gstar = 5 - (ystar + hstar);
		  
		  int[] stars = new int[3];
		  
		  stars[0] = ystar;
		  stars[1] = hstar;
		  stars[2] = gstar;
		  
		  return stars;
		
		  
		  
		  
	}
	public static void getStar2(double star, ProductDto pdto) {
		
		int ystar = 0, gstar = 0, hstar = 0;
		
		  ystar = (int)star;
		  double imsi = star - ystar;
		  
		  if(imsi >= 0.8) {
			 ystar++;
		  }else if(imsi >= 0.2 && imsi < 0.8) {
			 hstar = 1;
		  }
		  gstar = 5 - (ystar + hstar);
		  
		  int[] stars = new int[3];
		  
		  pdto.setYstar(ystar);
		  pdto.setHstar(hstar);
		  pdto.setGstar(gstar);
		    
	}
	public static String getBaeDay(int baeday) {
	LocalDate today = LocalDate.now();
		
		LocalDate xday = today.plusDays(baeday);
		int month = xday.getMonthValue();
		int day = xday.getDayOfMonth();
		
		int dday = xday.getDayOfWeek().getValue();
		String yoil = null;
		switch(dday) {
			case 1: yoil = "월"; break;
			case 2: yoil = "화"; break;
			case 3: yoil = "수"; break;
			case 4: yoil = "목"; break;
			case 5: yoil = "금"; break;
			case 6: yoil = "토"; break;
			case 7: yoil = "일"; break;
		}
		String baedayStr = "";
		if(baeday == 1) {
			baedayStr = "tomorrow" + "(" + yoil + ") 도착예정";
		}else if(baeday == 2) {
			baedayStr = "모레" + "(" + yoil + ") 도착예정"; 
		}else {
			baedayStr = month + "/" + day + "(" + yoil + ") 도착예정";
		}
		
		return baedayStr;
		
	}
	
	// 배송일이 넘어오면 배송예정 문자열 생성 리턴
	public static void getBaeday(ProductDto pdto) {
		LocalDate today = LocalDate.now();
		
		LocalDate xday = today.plusDays(pdto.getBaeday());
		int month = xday.getMonthValue();
		int day = xday.getDayOfMonth();
		
		int dday = xday.getDayOfWeek().getValue();
		String yoil = null;
		switch(dday) {
			case 1: yoil = "월"; break;
			case 2: yoil = "화"; break;
			case 3: yoil = "수"; break;
			case 4: yoil = "목"; break;
			case 5: yoil = "금"; break;
			case 6: yoil = "토"; break;
			case 7: yoil = "일"; break;
		}
		String baedayStr = "";
		if(pdto.getBaeday() == 1) {
			baedayStr = "tomorrow" + "(" + yoil + ") 도착예정";
		}else if(pdto.getBaeday() == 2) {
			baedayStr = "모레" + "(" + yoil + ") 도착예정"; 
		}else {
			baedayStr = month + "/" + day + "(" + yoil + ") 도착예정";
		}
		
		pdto.setBaedayStr(baedayStr);
	}
	public static String comma(int num) {
		DecimalFormat df = new DecimalFormat("#,###");
		
		return df.format(num);
	}
	
	public static String getState(int num) {
		String str = "";
		switch(num) {
			case 0 : str="결제완료"; break;
			case 1 : str="배송준비"; break;
			case 2 : str="취소신청"; break;
			case 3 : str="취소완료"; break;
			case 4 : str="배송중"; break;
			case 5 : str="배송완료"; break;
			case 6 : str="반품신청"; break;
			case 7 : str="반품완료"; break;
			case 8 : str="교환신청"; break;
			case 9 : str="교환완료"; break;
		}
		
		 return str;
	}
	public void userid() {
		// 문자열 자르고 나머지 문자에 *표처리 하는 방법 중 하나
		String userid = "asdf";
		String uid = userid.substring(0,2);
		for(int i = 2; i < userid.length(); i++) {
			uid = uid + "*";
		}
		System.out.println(uid);
	}
}
