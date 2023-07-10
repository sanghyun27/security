package com.study.security_sanghyun.web.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.study.security_sanghyun.service.notice.PrincipalDetails;

@Controller
public class PageController {
	
	// Model은 컨트롤러에서만 쓸 수 있음
	// @AuthenticationPrincipal <- PrincipalDetails principalDetails 객체를 받기 위해 사용, RestController, 그냥 컨트롤러 둘 다 쓸 수 있음
	@GetMapping({"/", "/index"})
	public String loadIndex(Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) {
		model.addAttribute("principal", principalDetails);
		return "index";	// model에 html띄워줌
	}
	
	@GetMapping("/auth/signin")
	public String loadSignin() {
		return "auth/signin";
	}
	
	@GetMapping("/auth/signup")
	public String loadSignup() {
		return "auth/signup";
	}
	
	@GetMapping("/mypage")
	public String loadMypage() {
		return "mypage";
	}

}
