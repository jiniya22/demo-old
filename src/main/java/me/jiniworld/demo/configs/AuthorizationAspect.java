package me.jiniworld.demo.configs;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.regex.Pattern;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;

import lombok.RequiredArgsConstructor;
import me.jiniworld.demo.models.values.TokenConfig;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.WeakKeyException;
import lombok.Setter;
import me.jiniworld.demo.exception.AuthorizationHeaderNotExistsException;
import me.jiniworld.demo.exception.InvalidTokenException;
import me.jiniworld.demo.exception.TokenExpiredException;

@RequiredArgsConstructor
@Aspect
@Component
public class AuthorizationAspect {
	
	private final TokenConfig tokenConfig;
	
	@Before("execution(public * me.jiniworld.demo.controllers.api.v1..*Controller.*(..)) ")
	public void insertAdminLog(JoinPoint joinPoint) throws WeakKeyException, UnsupportedEncodingException, TokenExpiredException {
		SecretKey key = Keys.hmacShaKeyFor(tokenConfig.getSecretKey().getBytes("UTF-8"));
		
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();		
		String authorization = request.getHeader("Authorization");
		if(StringUtils.isBlank(authorization)){ 
			throw new AuthorizationHeaderNotExistsException();
		}
		if(Pattern.matches("^Bearer .*", authorization)) {
			authorization = authorization.replaceAll("^Bearer( )*", "");
			Jws<Claims> jwsClaims = Jwts.parserBuilder()
					.setSigningKey(key)
					.build()
					.parseClaimsJws(authorization);
			
			if(jwsClaims.getBody() != null) {
				Claims claims = jwsClaims.getBody();
				if(!claims.containsKey("apiKey") || !tokenConfig.getApiKey().equals(claims.get("apiKey").toString())
						|| claims.getExpiration() == null) {
					throw new InvalidTokenException();
				} 
				long exp = claims.getExpiration().getTime();
				if(exp < new Date().getTime()) {
					throw new TokenExpiredException();
				}
			}
		} else {
			throw new InvalidTokenException();
		}
	}	
}
