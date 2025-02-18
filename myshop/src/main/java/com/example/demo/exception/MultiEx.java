package com.example.demo.exception;

public class MultiEx {
	public static void main(String[] args) {
		
	try {	
		int total = 390;
		int num = 2;
		
		int result = total / num;
		
		String str2 = "java";
		
		str2 = str2.replace("java", "Action");
		System.out.println(str2);
		
		int[] num2  = new int[6];
		num2[0] = 100;
		
		String bb ="33a";
		int n = Integer.parseInt(bb);
		//num2[6] = 99;
	}catch(ArithmeticException e) { // 무조건 발생할 예외클래스를 사용해야 한다.
		System.out.println(e.getMessage());
	}catch(NullPointerException e) {
		e.printStackTrace();
	}catch(ArrayIndexOutOfBoundsException e) {
		System.out.println("배열 범위 초과");
	}catch(Exception e) { // 예외 클래스의 최상위 클래스 => 모든 예외를 다 받는다.
		System.out.println("모든 예외 처리");
	}
		System.out.println("다음 작업");
	
	}
}
