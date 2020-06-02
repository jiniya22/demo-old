package me.jiniworld.demo.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import me.jiniworld.demo.configs.TestConfig;

@RequestMapping(value = "/test")
@RequiredArgsConstructor
@RestController
public class TestController {
	
	private final PasswordEncoder passwordEncoder;
	private final TestConfig testConfig;
	private String demoApi;
	
	@GetMapping(value = "/encode/{password}", produces = "text/plain")
	public String selectStore(@PathVariable("password") String password) {
		return passwordEncoder.encode(password);
	}
	
	@GetMapping(value="/config", produces = "text/plain")
	public Object testConfig() {
		return String.format("%s\n%s", demoApi, testConfig);
	}
	
	@Value("${demo.api}")
    public void setKEY(String demoApi) {
       this.demoApi = demoApi;
    }
}
