package me.jiniworld.demo.controllers;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.InvalidKeyException;
import io.jsonwebtoken.security.Keys;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import me.jiniworld.demo.models.responses.BasicResponse;
import me.jiniworld.demo.models.responses.ErrorResponse;

@RequestMapping(value = "/token")
@Tag(name = "token", description = "Bearer JWT Token API")
@ConfigurationProperties(prefix = "demo.token")
@RequiredArgsConstructor
@RestController
public class TokenController {

	@Setter private String typ;
	@Setter private String alg;
	@Setter private String apiKey;
	@Setter private String secretKey;
	
	@Operation(description = "JWT token 생성")
	@PostMapping(value = "")
	public ResponseEntity<? extends BasicResponse> createToken(@RequestBody TokenRequest tokenRequest) throws InvalidKeyException, UnsupportedEncodingException {
		if(StringUtils.isBlank(tokenRequest.getApikey()) || StringUtils.isBlank(tokenRequest.getNonce())) {
			return ResponseEntity.badRequest().body(new ErrorResponse("apikey 또는 nonce가 없습니다.", "400"));
		} else if(!apiKey.equals(tokenRequest.getApikey())) {
			return ResponseEntity.badRequest().body(new ErrorResponse("apikey가 일치하지 않습니다.", "400"));
		}
		
		try {
			Date nonce = new Date(Long.valueOf(tokenRequest.getNonce()));
			Calendar c = Calendar.getInstance();
			c.add(Calendar.MINUTE, -30);
			if(nonce.getTime() < c.getTime().getTime() || new Date().getTime() < nonce.getTime())
				return ResponseEntity.badRequest().body(new ErrorResponse("nonce값이 유효하지 않습니다.", "400"));
		} catch(Exception e) {
			return ResponseEntity.badRequest().body(new ErrorResponse("nonce값은 millisecond값으로 설정해야 합니다.", "400"));
		}
		
		SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes("UTF-8"));
		
		Map<String, Object> header = new HashMap<>();
		header.put("typ", typ);
		header.put("alg", alg);
		
        String jwt = Jwts.builder()
        		.setHeader(header)
        		.setIssuer("demoApp")
        		.setIssuedAt(new Date())
        		.setExpiration(new Date(Long.valueOf(tokenRequest.getNonce()) + 30000L)) // 1800000L
        		.claim("apiKey", apiKey)
                .signWith(key)  
                .compact();
        return ResponseEntity.ok().body(new TokenResponse(jwt));
    }
	
	@Getter @Setter
	@Schema(name = "TokenRequest")
	public static class TokenRequest {
		private String apikey;
		@Schema(description = "현재시각(단위: millisecond)") private String nonce;
	}
	
	@Getter
	public class TokenResponse extends BasicResponse {
		@Schema(description = "JWT 토큰") private String token;
		
		public TokenResponse(String token) {
			this.token = token;
		}
	}
}
