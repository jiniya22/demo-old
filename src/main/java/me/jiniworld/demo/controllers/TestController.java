package me.jiniworld.demo.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

	@GetMapping("/")
	public String index() {
		return "Demo project!!";
	}
}
