<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
  <style>
    #sub {
      position:absolute;
      left:0px;
      top:30px;
      width:100px;
      visibility:hidden;
      padding-left:0px;
      background:blue;
    }
  </style>
  <script>
   var chk=0;
   function view()
   {
	   if(chk%2==1)
	   {
		   document.getElementById("sub").style.visibility="hidden";
	   }	
	   else
	   {
		   document.getElementById("sub").style.visibility="visible";
	   }
	   chk++;
	   /*
	   if(chk==1)
	   {
		   document.getElementById("sub").style.visibility="hidden";
		   chk=0;
	   }	
	   else
	   {
		   document.getElementById("sub").style.visibility="visible";
		   chk=1;
	   }
	   */
	   //alert(document.getElementById("sub").style.visibility);
	   /*
	   if(document.getElementById("sub").style.visibility=="visible")
	   {
		   document.getElementById("sub").style.visibility="hidden";
	   }	
	   else
	   {
		   document.getElementById("sub").style.visibility="visible";
	   }
	   */
   }
   
   
  </script>
</head>
<body> <!-- etc/menu2.jsp -->
  <ul style="margin:auto;width:200px;padding-left:0px;">
   <li style="position:relative;background:red;height:30px;" onclick="view()"> 가전제품 
      <ul id="sub">
        <li> TV </li>
        <li> 냉장고 </li>
    	<li> 세탁기 </li>
    	<li> 컴퓨터 </li>
    	<li> 청소기 </li>
  	  </ul>   
   </li>
  </ul> 
   <!-- 가전제품에 마우스가 올라가면 TV, 냉장고, 세탁기, 컴퓨터, 청소기 메뉴를 나타내기
                        나가면 사라지기
       1. 하위메뉴를 상위메뉴 태그 내에 위치시킨다
       2. 상위메뉴 태그의 속성에 position을 추가한다 => 페이지내에 위치되어 있다면 => relative로 하면 된다.
       3. 하위메뉴는 position을 absolute로 하고 적당한 left,top의 값으로 배치시킨다
       4. 상위메뉴에 마우스 올라가면 하위메뉴의 visibility:visible , 내려가면 visibility:hidden
    -->
   
</body>
</html>