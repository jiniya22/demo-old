package me.jiniworld.demo.controllers.views;

import javax.servlet.http.HttpSession;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
	
	@GetMapping(value = "/")
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
		if(user != null && user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_VIEW"))) {
			return "redirect:/v";
		}
		return "login/login";
	}
	
	@GetMapping(value = "/err/denied-page")
	public String accessDenied(){
		return "err/deniedPage";
	}
	
	@GetMapping(value = "/join")
	public String join(@AuthenticationPrincipal User user){
		if(user != null && user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_VIEW"))) {
			return "redirect:/v";
		}
		return "login/join";
	}
}
