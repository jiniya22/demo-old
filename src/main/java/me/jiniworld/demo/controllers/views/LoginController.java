package me.jiniworld.demo.controllers.views;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import me.jiniworld.demo.models.entities.SecurityUser;
import me.jiniworld.demo.models.entities.UserRole.RoleType;
import me.jiniworld.demo.models.values.UserValue;
import me.jiniworld.demo.services.UserService;

@Controller
public class LoginController {
	
	private final UserService userService;
	
	@Autowired
	public LoginController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping(value = "/")
	public String index(@AuthenticationPrincipal SecurityUser securityUser){
		if(securityUser != null) {
			if(securityUser.getRoleTypes().contains(RoleType.ROLE_VIEW)) {
				return "redirect:/v";
			}
		}
		return "redirect:/login";
	}
	
	@GetMapping(value = "/login")
	public String login(@AuthenticationPrincipal SecurityUser securityUser){
		if(securityUser != null && securityUser.getRoleTypes().contains(RoleType.ROLE_VIEW)) {
			return "redirect:/v";
		}
		return "login/login";
	}
	
	@GetMapping(value = "/err/denied-page")
	public String accessDenied(){
		return "err/deniedPage";
	}
	
	@GetMapping(value = "/join")
	public String joinForm(@AuthenticationPrincipal SecurityUser securityUser){
		if(securityUser != null && securityUser.getRoleTypes().contains(RoleType.ROLE_VIEW)) {
			return "redirect:/v";
		}
		return "login/join";
	}
	
	@ResponseBody
	@PostMapping(value = "/join")
	public Map<String, Object> join(@RequestBody UserValue value){
		Map<String, Object> response = new HashMap<>();
		response.put("success", userService.join(value) != null ? true : false);
		return response;
	}
	
}
