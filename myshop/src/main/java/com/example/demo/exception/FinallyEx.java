package com.example.demo.exception;

public class FinallyEx {
	public static void main(String[] args) {
		try {
			int total = 390;
			int num = 0;
			
			int result = total / num;
		}catch(ArithmeticException e){// try문에 발생할 예외를 예외별로 처리할 수 있다.
			
			System.out.println("0이 입력되어 계산 불가능");
		}finally { // 예외가 발생해도 안해도 무조건 실행되는 구문
				   // 해당 구문은 try구문과 연관된 구문이다.
			System.out.println("finally 실행");
		}
		
		System.out.println("다음 작업");
	}
}
