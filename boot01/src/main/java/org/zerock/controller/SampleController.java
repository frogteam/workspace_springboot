package org.zerock.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.domain.SampleVO;

// 스프링의 빈(bean)으로 등록되도록 한다.
@RestController
public class SampleController {

	@GetMapping("/hello")
	public String sayHello() {
		return "Hello World";
	}
	
	@GetMapping("/sample")
	public SampleVO makeSample() {
		SampleVO vo = new SampleVO();
		vo.setVal1("v1");
		vo.setVal2("v2");
		vo.setVal3("v3");
		
		System.out.println(vo);
		
		return vo;
	}
	
}
