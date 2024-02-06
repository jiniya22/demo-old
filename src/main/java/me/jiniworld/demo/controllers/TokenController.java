package me.jiniworld.demo.controllers;

import io.jsonwebtoken.Jwts;
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
import me.jiniworld.demo.models.values.TokenConfig;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.SecretKey;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RequestMapping(value = "/token")
@Tag(name = "token", description = "Bearer JWT Token API")
@RequiredArgsConstructor
@RestController
public class TokenController {
	private final TokenConfig tokenConfig;
	
	@Operation(description = "JWT token 생성")
	@PostMapping(value = "")
	public ResponseEntity<BasicResponse> createToken(@RequestBody TokenRequest tokenRequest) throws InvalidKeyException, UnsupportedEncodingException {
		if(StringUtils.isBlank(tokenRequest.getApikey()) || StringUtils.isBlank(tokenRequest.getNonce())) {
			return ResponseEntity.badRequest().body(new ErrorResponse("apikey 또는 nonce가 없습니다.", "400"));
		} else if(!tokenConfig.getApiKey().equals(tokenRequest.getApikey())) {
			return ResponseEntity.badRequest().body(new ErrorResponse("apikey가 일치하지 않습니다.", "400"));
		}
		
		Date now = new Date();
		long nonce = Long.parseLong(tokenRequest.getNonce());
		long expTime = 1800000L;	// 유효시간 : 30m
		try {
			if(nonce < now.getTime() - expTime)
				return ResponseEntity.badRequest().body(new ErrorResponse("nonce값이 유효하지 않습니다.", "400"));
		} catch(Exception e) {
			return ResponseEntity.badRequest().body(new ErrorResponse("nonce값은 millisecond값으로 설정해야 합니다.", "400"));
		}
		
		SecretKey key = Keys.hmacShaKeyFor(tokenConfig.getSecretKey().getBytes(StandardCharsets.UTF_8));
		
		Map<String, Object> header = new HashMap<>();
		header.put("typ", tokenConfig.getTyp());
		header.put("alg", tokenConfig.getAlg());
		
        String jwt = Jwts.builder()
        		.setHeader(header)
        		.setIssuer("demoApp")
        		.setIssuedAt(now)
        		.setExpiration(new Date(nonce + expTime))
        		.claim("apiKey", tokenConfig.getApiKey())
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
	public static class TokenResponse extends BasicResponse {
		@Schema(description = "JWT 토큰") private String token;
		
		public TokenResponse(String token) {
			this.token = token;
		}
	}
}
