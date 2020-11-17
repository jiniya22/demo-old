package me.jiniworld.demo.configs;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;

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
import me.jiniworld.demo.exception.TokenExpiredException;

@ConfigurationProperties(prefix = "demo.token")
@Aspect
@Component
public class AuthorizationAspect {
	
	@Setter private String apiKey;
	@Setter private String secretKey;
	
	@Before("execution(public * me.jiniworld.demo.controllers.api.v1..*Controller.*(..)) ")
	public void insertAdminLog(JoinPoint joinPoint) throws WeakKeyException, UnsupportedEncodingException, TokenExpiredException {
		SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes("UTF-8"));
		
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();		
		String authorization = request.getHeader("Authorization");
		if(StringUtils.isBlank(authorization)){ 
			throw new AuthorizationHeaderNotExistsException();
		}
		if(authorization.indexOf("Bearer") >= 0) {
			authorization = authorization.replaceAll("^Bearer( )*", "");
			Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(key)
            .build()
            .parseClaimsJws(authorization);
			
			if(claims.getBody() != null) { 
				if(claims.getBody().getExpiration() != null) {
					long exp = claims.getBody().getExpiration().getTime();
					if(exp < new Date().getTime()) {
						throw new TokenExpiredException();
					}
				}
			}
		}
	}
	
}
