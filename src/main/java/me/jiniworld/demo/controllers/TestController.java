package me.jiniworld.demo.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import me.jiniworld.demo.configs.TestConfig;

@RequestMapping(value = "/test", produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
@RestController
public class TestController {
	
	private final PasswordEncoder passwordEncoder;
	private final TestConfig testConfig;
	
	@GetMapping("/encode/{password}")
	public String selectStore(@PathVariable("password") String password) {
		return passwordEncoder.encode(password);
	}
	
	@GetMapping("/config")
	public Object configMail(@Value("${demo.api}") String demoApi) {
		return String.format("%s\n%s", demoApi, testConfig.toString());
	}
}
