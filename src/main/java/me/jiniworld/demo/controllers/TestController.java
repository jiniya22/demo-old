package me.jiniworld.demo.controllers;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "test", description = "테스트 API")
@RequestMapping(value = "/test")
@RequiredArgsConstructor
@RestController
public class TestController {
	
	private final PasswordEncoder passwordEncoder;
	
	@Operation(summary = "BCrypt 해쉬함수로 암호화")
	@GetMapping(value = "/encode/{password}", produces = "text/plain")
	public String selectStore(@Parameter(description = "암호화할 문자열") @PathVariable("password") String password) {
		return passwordEncoder.encode(password);
	}
	
	@GetMapping(value = "/tttt")
	public Map<String, Object> tttt(HttpServletRequest request) throws Exception {
		Map<String, Object> res = new HashMap<>();
		res.put("serverName", request.getServerName());
		res.put("servletPath", request.getServletPath());
		res.put("requestURL", request.getRequestURL());
		res.put("requestURI", request.getRequestURI());
		res.put("remoteAddr", request.getRemoteAddr());
		res.put("remoteHost", request.getRemoteHost());
		res.put("X-Forwarded-For", request.getHeader("X-Forwarded-For"));
		res.put("X-Forwarded-Host", request.getHeader("X-Forwarded-Host"));
		res.put("X-Forwarded-Protocol", request.getHeader("X-Forwarded-Protocol"));
		res.put("Proxy-Client-IP", request.getHeader("Proxy-Client-IP"));
		res.put("WL-Proxy-Client-IP", request.getHeader("WL-Proxy-Client-IP"));
		res.put("HTTP_CLIENT_IP", request.getHeader("HTTP_CLIENT_IP"));
		res.put("HTTP_X_FORWARDED_FOR", request.getHeader("HTTP_X_FORWARDED_FOR"));
		return res;
	}
	
	@Operation(summary = "현재 시각 millisecond로 반환")
	@GetMapping(value = "/mili", produces = "text/plain")
	public String selectStore() {
		return String.valueOf(new Date().getTime());
	}
	
}
