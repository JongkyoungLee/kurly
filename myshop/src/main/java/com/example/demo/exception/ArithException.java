package com.example.demo.exception;

public class ArithException {

	public static void main(String[] args) {
		
		// 예외처리 : 잘못된 동작 발생가능성이 있을 때 전체프로그램을 중지시키지 않고 예외가 발생된걸 처리한다.
		// 1. try ~ catch : 
		// 2. throws
		try {
			int total = 390;
			int num = 0;
			
			int result = total / num;
		}catch(ArithmeticException e){// try문에 발생할 예외를 예외별로 처리할 수 있다.
			
			System.out.println("0이 입력되어 계산 불가능");
		}
	}
}
