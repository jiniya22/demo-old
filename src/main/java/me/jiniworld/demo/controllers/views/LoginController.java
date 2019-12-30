package me.jiniworld.demo.controllers.views;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import me.jiniworld.demo.models.entities.SecurityUser;

@Controller
public class LoginController {
	
	@GetMapping(value = "/")
	public String index(@AuthenticationPrincipal SecurityUser user){
		if(user != null) {
			if(user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_VIEW"))) {
				return "redirect:/v";
			}
		}
		return "redirect:/login";
	}
	
	@GetMapping(value = "/login")
	public String login(@AuthenticationPrincipal SecurityUser user){
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
	public String join(@AuthenticationPrincipal SecurityUser user){
		if(user != null && user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_VIEW"))) {
			return "redirect:/v";
		}
		return "login/join";
	}
}
