package com.example.demo.dto;

import lombok.Data;

@Data
public class GumaeDto {
		private int id,baeId,su,useJuk,sudan,card1,halbu1,bank1,card2,tel,bank2, state;
	    private String userid,pcode,jumuncode,writeday;
	    
	    // 수량이 여러개일 경우 "/"로 구분하는 su String 자료형을 새로만든다.
	    private String sus;
}
