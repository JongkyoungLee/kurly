package com.example.demo.exception;

public class ThrowsMain {
	public String chgString(String str) throws NullPointerException {
		str = str.replace("aa", "bb");
		
		return str;
		
		/*	
		try {
			str = str.replace("aa", "bb");
			
		}catch(NullPointerException e) {
			str="null 값 입니다.";
		}
		
		return str;
	}
	*/
	}
}