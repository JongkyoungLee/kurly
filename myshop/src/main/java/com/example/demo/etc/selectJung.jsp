<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
  <script>
     
     
     function getJung()
     {
    	  
    	 
    	 var imsi='[{"name":"TV","code":"01"},{"name":"냉장고","code":"02"},{"name":"세탁기","code":"03"}]';
         
         var jungs=JSON.parse(imsi);
         
         if(document.pform.jung.length!=jungs.length+1)
	         for(jung of jungs)
	         {
	        	 var opt=document.createElement("option");
	        	 opt.setAttribute("value",jung.code);
	        	 opt.textContent=jung.name;
	        	 
	        	 document.pform.jung.appendChild(opt);
	         }	 
         /*
         var str="<option value=''> 중분류 </option>";
         
         for(jung of jungs)
         {
        	 str=str+"<option value='"+jung.code+"'>"+jung.name+"</option>";
        	         // <option value='01'> TV </option>
         }	 
         
         document.pform.jung.innerHTML=str;
         */
         
         /*
         document.pform.jung.options.length=jungs.length+2;  // option태그의 갯수
         var i=1;
         for(jung of jungs)
         {
        	 document.pform.jung.options[i].value=jung.code;
        	 document.pform.jung.options[i].text=jung.name;
        	 i++;
         }
         */
     }
    
     
  </script>
</head>
<body>  <!-- etc/selectJung.jsp -->
  
  <input type="button" onclick="getJung()" value="가져오기">
  <form name="pform">
     <select name="jung">
       <option> 중분류 </option>
     </select>
  </form>
</body>
</html>