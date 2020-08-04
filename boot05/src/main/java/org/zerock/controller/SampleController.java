package org.zerock.controller;

import java.sql.Timestamp;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.zerock.domain.MemberVO;

@Controller
public class SampleController {
	@GetMapping("/sample1")
	public void sample1(Model model) {
		//model.addAttribute("greeting", "Hello World");
		model.addAttribute("greeting", "안녕하세요");
	}
	
	@GetMapping("/sample2")
	public void sample2(Model model) {
		MemberVO vo = new MemberVO(123, "u00", "p00", "홍길동", 
				new Timestamp(System.currentTimeMillis()));
		
		model.addAttribute("vo", vo);
	}
}
