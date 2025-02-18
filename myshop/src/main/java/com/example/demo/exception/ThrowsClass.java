package com.example.demo.exception;

public class ThrowsClass {
	
	public static void main(String[] args) {
		String str = "abcdeaaoobbaa";
		ThrowsMain throwsMain = new ThrowsMain();
		str=throwsMain.chgString(str);
		
		System.out.println(str);
		String str2;
		try {
			str2 = null;
			str2 = throwsMain.chgString(str2);
			System.out.println(str2);			
		}catch(NullPointerException e) {
			str2 = "null값이 온거 같네요";
		}
		System.out.println(str2);
		System.out.println("다음 작업");
	}
}
