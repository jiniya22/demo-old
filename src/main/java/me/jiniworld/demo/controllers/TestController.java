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

import me.jiniworld.demo.models.entities.Store;
import me.jiniworld.demo.models.entities.User;
import me.jiniworld.demo.services.StoreService;
import me.jiniworld.demo.services.UserService;

@RequestMapping(value = "/test", produces = {MediaType.APPLICATION_JSON_VALUE})
@RestController
public class TestController {
	
	private final StoreService storeService;
	private final UserService userService;
	
	@Autowired
	public TestController(StoreService storeService, UserService userService) {
		this.storeService = storeService;
		this.userService = userService;
	}
	
	
	@GetMapping("/users/{id}")
	public Map<String, Object> selectUser(@PathVariable("id") long id) {
		Map<String, Object> response = new HashMap<>();
		
		Optional<User> oUser = userService.findByIdUsingMapper(id);
		
		if(oUser.isPresent()) {
			response.put("user", oUser);
			response.put("result", "SUCCESS");
		} else {
			response.put("result", "FAIL");
		}
		
		return response;
	}
	
	@GetMapping("/users/{id}/2")
	public Map<String, Object> selectUser2(@PathVariable("id") long id) {
		Map<String, Object> response = new HashMap<>();
		
		Optional<User> oUser = userService.findByIdUsingMapper2(id);
		
		if(oUser.isPresent()) {
			response.put("user", oUser);
			response.put("result", "SUCCESS");
		} else {
			response.put("result", "FAIL");
		}
		
		return response;
	}
	
	@GetMapping("/stores/{id}")
	public Map<String, Object> selectStore(@PathVariable("id") long id) {
		Map<String, Object> response = new HashMap<>();
		
		Optional<Store> oStore = storeService.findByIdUsingMapper(id);
		
		if(oStore.isPresent()) {
			response.put("store", oStore.get());
			response.put("result", "SUCCESS");
		} else {
			response.put("result", "FAIL");
		}
		
		return response;
	}
}
