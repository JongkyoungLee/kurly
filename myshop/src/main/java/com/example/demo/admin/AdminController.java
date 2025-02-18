package com.example.demo.admin;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.example.demo.MyUtil;
import com.example.demo.dto.CompanyDto;
import com.example.demo.dto.DaeDto;
import com.example.demo.dto.GumaeDto;
import com.example.demo.dto.JungDto;
import com.example.demo.dto.ProductDto;
import com.example.demo.dto.SoDto;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class AdminController {

	@Autowired
	private AdminMapper mapper;
	
	@GetMapping("/admin/proWrite")
	public String proWrite(Model model) {
		
		//dae테이블 읽어오기
		ArrayList<DaeDto> dlist = mapper.getDae();
		model.addAttribute("dlist",dlist);
	
		ArrayList<CompanyDto> clist = mapper.getCompany();	
		model.addAttribute("clist", clist);
		
		return "/admin/proWrite";
	}
	
	@GetMapping("/admin/getJung")
	public @ResponseBody ArrayList<JungDto> getJung(HttpServletRequest request) {
		
		String dae = request.getParameter("dae");
		
		return mapper.getJung(dae);
	}
	@GetMapping("/admin/getSo")
	public @ResponseBody ArrayList<SoDto> getSo(HttpServletRequest request){
		String daejung = request.getParameter("daejung");
		
		return mapper.getSo(daejung);
	}

	@GetMapping("/admin/getPcode")
	public @ResponseBody String getPcode(HttpServletRequest request) {
		String pcode = request.getParameter("pcode");
		int num = mapper.getNumber(pcode);
		
		String imsi = String.format("%03d", num);
		pcode = pcode + imsi;
		System.out.println(num);
		
		return pcode;
	}
	@PostMapping("admin/proWriteOk")
	public String proWriteOk(ProductDto pdto, MultipartHttpServletRequest multi) throws Exception {
		// product테이블에 저장 => 파일의 이름
		// pimg(메인그림) , dimg(상세그림) => 저장된 파일명을 생성
		Iterator<String> iter =multi.getFileNames(); // type="file"의 name을 가져온다.
		String pimg = "";
		String dimg = "";
		while(iter.hasNext()) {
			//System.out.println(iter.next());
			String imsi = iter.next();
			
		    MultipartFile file = multi.getFile(imsi);
		    // 파일명을 저장
		    if(!file.isEmpty()) {
		    	 String oname = file.getOriginalFilename();
		    	 if(imsi.equals("exdimg")) {		    	
				    	dimg = oname;		    	
				    }else {
				    	pimg = pimg + oname +"/";
				    }
				    // 실제로 업로드된 파일을 저장폴더(static/product)에 저장
				   String str =  ResourceUtils.getFile("classpath:static/product").toString();
				   
				   // 파일의 유무는 파일의 객체를 생성해서 한다.
				   File sfile = new File(str+"/"+oname);
				   while(sfile.exists()) {
					   // 새 파일 생성하는 방법
					    String[] fnames = oname.split("[.]"); 
					    
					    oname = fnames[0]+System.currentTimeMillis()+"."+fnames[1];
					   // 중복되는 파일이름이 있을경우 새 파일명을 생성 후 다시 파일 객체를 생성한다.
					    sfile = new File(str + "/" + oname);
					   
				   }
				   Path path = Paths.get(str+"/"+oname);
				   Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
		    }
		   
		   
		   // type="file"에 사진을 선택하지 않았을 경우
		   // 업로드된 파일이 존재한다면 파일명을 변경하기
		   // 1. pimg, dimg 값을 dto에 저장 후 mapper에 전송하여 테이블에 저장
		   // 2. type="file"에 사진을 선택하지 않았을 경우
		   // 3. 업로드된 파일이 존재한다면 파일명을 변경하기
		}
		System.out.println(pimg);
		System.out.println(dimg);
		return "/admin/proList";
	}
	@GetMapping("/admin/sellList")
	public String sellList(HttpSession session, Model model) {
		// 관리자인지 검사하는거 나중에 하기
	  ArrayList<HashMap> adminMap = mapper.sellList();
	  model.addAttribute("adminMap",adminMap);
	  
	  for(int i = 0; i < adminMap.size(); i++) {
		 HashMap map = adminMap.get(i);
		 int state = Integer.parseInt(map.get("state").toString());
		 String stateStr = MyUtil.getState(state);
		 
		 // mamp에 추가 => map.put("키","값");
		 map.put("stateStr", stateStr);
		 
	  }
	  
	  return "/admin/sellList";
	  
		
	}
	@GetMapping("/admin/chgState")
	public String chgState(GumaeDto gdto) {
		mapper.chgState(gdto);
		
		return "redirect:/admin/sellList";
	}
	
}
