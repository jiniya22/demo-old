package me.jiniworld.demo.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import me.jiniworld.demo.models.entities.User;
import me.jiniworld.demo.services.UserService;

@RequestMapping(value = "/users", produces = {MediaType.APPLICATION_JSON_VALUE})
@RestController
public class UserController {
	
	private final UserService userService;
	
	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping("/{id}")
	public Map<String, Object> findById(@PathVariable("id") long id) {
		Map<String, Object> response = new HashMap<>();
		
		Optional<User> oUser = userService.findById(id);
		if(oUser.isPresent()) {
			response.put("result", "SUCCESS");
			response.put("user", oUser.get());
		} else {
			response.put("result", "FAIL");
			response.put("reason", "일치하는 회원 정보가 없습니다. 사용자 id를 확인해주세요.");
		}
		
		return response;
	}
}
