<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
  <script>
    function NaNCheck(val)
    {
    	// isNaN() : 숫자가 아니냐 => 숫자 false , 문자 true
    	//alert( isNaN(val) );
    	if(isNaN(val))
    	{
    		document.pform.su.value="";
    	}	
    }
    
    var str="java는 좋아요!!  java는 어렵지만 좋아   민규씨 java";
    //str=str.replace("java","C++"); // C++는 좋아요!!  java는 어렵지만 좋아   민규씨 java
    str=str.replace(/java/g,"C++"); //C++는 좋아요!!  C++는 어렵지만 좋아   민규씨 C++
    //alert(str);  
    
    var str2="k16asf364854adf";   // 16364854
    str2=str2.replace( /[^0-9]/g ,"");
    //alert(str2);
    
    
    function chgNumber(my) // my = document.pform.price
    {
    	my.value=my.value.replace(/[^0-9]/g,"");
    }
    function chgString(my)
    {
    	my.value=my.value.replace(/[0-9]/g,"");
    }
    function chgHan(my)
    {
    	my.value=my.value.replace(/[A-Za-z]/g,"");
    }
    function chgEng(my)
    {
    	my.value=my.value.replace(/[^A-Za-z]/g,"");
    }
  </script>
</head>
<body> <!-- etc/numberString.jsp -->
  <form name="pform">
    수량 <input type="text" name="su" onblur="NaNCheck(this.value)"> <p>
    가격 <input type="text" name="price" onkeyup="chgNumber(this)"> <p>
    문자만 <input type="text" name="munja" onkeyup="chgString(this)"> <p>
    한글(영문제외) <input type="text" name="han" onkeyup="chgHan(this)"> <p>
    영문자만 <input type="text" name="eng" onkeyup="chgEng(this)"> <p>
    <input type="submit" value="저장">
  </form>
</body>
</html>







