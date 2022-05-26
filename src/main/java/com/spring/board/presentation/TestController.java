package com.spring.board.presentation;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.spring.board.test.TestServiceImpl;
import com.spring.board.test.TestVo;



@Controller
public class TestController {

	@Autowired
	private TestServiceImpl service;
	
	@RequestMapping("list.do")
	public String select(TestVo vo, Model model) {
		//System.out.println("Ȯ����======>" + vo.toString());
		model.addAttribute("li", service.select());
		
		return "list.jsp";
	}
	
	@RequestMapping("upload.do")
	public String insert() {		
		return "upload.jsp";
	}
	
	@RequestMapping("uploadOk.do")
	public String uploadOk(TestVo vo, HttpServletRequest request) throws Exception {
		
		//path ����
 		String path = request.getSession().getServletContext().getRealPath("/files/");
 		System.out.println(path);
 		
 		//���� ������ ����
 		MultipartFile imgUpdateFile = vo.getImgUploadFile();
 		String fileNmae = imgUpdateFile.getOriginalFilename();
 		File f = new File(path+fileNmae);
 		
 		//���� �ڸ���
 		String onlyFilename = "";
 		String extension = "";
 		
 		//��¥ �����ϱ�
 		long time = System.currentTimeMillis();
 		SimpleDateFormat dayTime = new SimpleDateFormat("HHmmss");
 		String timeStr = dayTime.format(time);

 		
 		//���� ������ ������
 		if(!imgUpdateFile.isEmpty()) {
 			//���� �ߺ� üũ
 			//���� ������ �̸��� ������ ������
 			if(f.exists()) {
 				onlyFilename = fileNmae.substring(0, fileNmae.indexOf("."));
 				extension = fileNmae.substring(fileNmae.indexOf(".")); 
 				fileNmae = onlyFilename + "_" + timeStr + extension ; 
 			  
 				imgUpdateFile.transferTo(new File(path+fileNmae));
 			
 			//�ߺ� ������ ������
 			} else {
 				imgUpdateFile.transferTo(new File(path+fileNmae));
 			}
 		}
 		
 		//���� ����
 		vo.setImg(fileNmae); 		 		 		
		service.insert(vo);
	
		return "list.do";
	}
}
