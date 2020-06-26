package me.jiniworld.demo.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	

	@PostMapping(value="/ddd")
	public Map<String, String> ddd(@RequestBody Map<String, String> map) {
		return map;
	}
	
	@PatchMapping(value="/baba")
	public Map<String, String> baba(@RequestBody Map<String, String> map) {
		return map;
	}
	
	@PutMapping(value="/abab")
	public Map<String, String> abab(@RequestBody Map<String, String> map) {
		return map;
	}	
	
	@GetMapping(value = "/encode/{password}", produces = "text/plain")
	public String selectStore(@PathVariable("password") String password) {
		return passwordEncoder.encode(password);
	}
	
	@PostMapping(value="/aaa")
	public Map<String, String> aaa(@RequestBody Map<String, String> map) {
		return map;
	}
	
	@GetMapping(value="/config", produces = "text/plain")
	public Object testConfig() {
		return String.format("%s\n%s", demoApi, testConfig);
	}
	
	@DeleteMapping(value="/bbb")
	public Map<String, String> bbb(@RequestBody Map<String, String> map) {
		return map;
	}
	
	@GetMapping(value = "/tttt")
	public Map<String, Object> tttt(HttpServletRequest request) throws Exception {
		Map<String, Object> res = new HashMap<>();
		res.put("serverName", request.getServerName());
		res.put("servletPath", request.getServletPath());
		res.put("requestURL", request.getRequestURL());
		res.put("requestURI", request.getRequestURI());
		res.put("requestURI", request.getRemoteAddr());	
		res.put("X-Forwarded-For", request.getHeader("X-Forwarded-For"));
		res.put("X-Forwarded-Host", request.getHeader("X-Forwarded-Host"));
		res.put("X-Forwarded-Protocol", request.getHeader("X-Forwarded-Protocol"));
		res.put("Proxy-Client-IP", request.getHeader("Proxy-Client-IP"));
		res.put("WL-Proxy-Client-IP", request.getHeader("WL-Proxy-Client-IP"));
		res.put("HTTP_CLIENT_IP", request.getHeader("HTTP_CLIENT_IP"));
		res.put("HTTP_X_FORWARDED_FOR", request.getHeader("HTTP_X_FORWARDED_FOR"));
		return res;
	}
	
	@Value("${demo.api}")
    public void setKEY(String demoApi) {
       this.demoApi = demoApi;
    }
}
