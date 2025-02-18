<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
  <script>
    function test1(my) // 
    {   // text, password, textarea인 경우 읽는 방법은 크게 3가지이다
    	alert(my.value);                   				 // name이 kor1인 입력폼에 입력된 값
    	alert(my);                                       // name이 kor1인 입력폼에 입력된 값
    	alert(document.aform.kor3.value); 				 // name이 kor3인 입력폼에 입력된 값
        alert(document.getElementById("kor4").value);    // name이 kor4인 입력폼에 입력된 값
    }
    
    function test2(my)
    {
    	alert(my.checked);  //체크속성
    	alert(my);          //체크속성
    	
    	document.getElementsByClassName("ok")[인덱스].checked
    	
    	document.aform.food[인덱스].checked;
    	document.aform.eng.checked;  // 하나일 경우는 배열이 아니다
    }
    
    function test3(my) // my
    {
    	//alert(my.value);
    	//alert(my);
    	
    	// 몇번째 option태그가 선택되었느냐?
    	//alert(document.aform.bank.selectedIndex);
    	//alert(document.getElementById("bank2").selectedIndex);
    	
    	alert(my.value);
    	alert(my.text);
    	alert(my[document.aform.bank.selectedIndex].value);
    }
  </script>
</head>
<body>
   <form name="aform">
     <input type="text" name="kor1" onclick="test1(this)" id="kor1"> <p>
     <input type="text" name="kor2" onclick="test1(this.value)" id="kor2"> <p>
     <input type="text" name="kor3" onclick="test1()" id="kor3"> <p>
     <input type="text" name="kor4" onclick="test1()" id="kor4"> <p>
     
     <input type="checkbox" name="eng">
 
     <input type="radio" name="food" value="짜장면" class="ok" onclick="test2(this)">짜장면
     <input type="radio" name="food" value="짬뽕"   class="ok" onclick="test2(this.checked)">짬뽕 <p>
     
     <select name="bank" id="bank2" onchange="test3(this)"> <!-- this.value -->
       <option value="우리"> 우리은행 </option>
       <option value="신한"> 신한은행 </option>
       <option value="농협"> 농협은행 </option>
       <option value="국민"> 국민은행 </option>
     </select>
   </form>
</body>
</html>