package me.jiniworld.demo.controllers.views;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
	
	@GetMapping(value = "/")
	public String index(){
		return "redirect:/login2";
	}
	
	@GetMapping(value = "/login2")
	public String loginForm(){
		return "login/login2";
	}
}
