package me.jiniworld.demo.controllers.views;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {
	
	@RequestMapping(value = "")
	public String index(@AuthenticationPrincipal User user){
		if(user != null) {
			if(user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_VIEW"))) {
				return "redirect:/v";
			}
		}
		return "redirect:/login";
	}
	
	@GetMapping(value = "/login")
	public String login(@AuthenticationPrincipal User user){
		if(user != null) {
			if(user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_VIEW"))) {
				return "redirect:/v";
			}
		}
		return "login/login";
	}

//	@GetMapping(value = "/login/access-denied")
//	public String accessDenied(){
//		return "login/accessDenied";
//	}
	
}
