package me.jiniworld.demo.controllers;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.InvalidKeyException;
import io.jsonwebtoken.security.Keys;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequestMapping(value = "/token")
@Tag(name = "token", description = "Bearer JWT Token API")
@ConfigurationProperties(prefix = "demo.token")
@RequiredArgsConstructor
@RestController
public class TokenController {

	@Setter private String typ;
	@Setter private String alg;
	@Setter private String secretKey;
	
	@Operation(description = "JWT token 생성")
	@GetMapping(value = "", produces = "text/plain")
	public String createToken() throws InvalidKeyException, UnsupportedEncodingException {
		SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes("UTF-8"));
		Map<String, Object> header = new HashMap<>(), payloads = new HashMap<>();
		header.put("typ", typ);
		header.put("alg", alg);
		
		payloads.put("user_id", "jini");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, 30);		// 유효시간 30분
		payloads.put("exp", cal.getTime().getTime());
		
        String jwt = Jwts.builder()
        		.setHeader(header)
        		.setClaims(payloads)
                .signWith(key)  
                .compact();
        return jwt;
    }
	
}
