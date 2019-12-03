package me.jiniworld.demo.commons.handlers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.csrf.InvalidCsrfTokenException;
import org.springframework.security.web.csrf.MissingCsrfTokenException;
import org.springframework.stereotype.Component;

@Component
public class WebAccessDeniedHandler implements AccessDeniedHandler {

	private static final Logger logger = LoggerFactory.getLogger(WebAccessDeniedHandler.class);
	
	@Override
	public void handle(HttpServletRequest req, HttpServletResponse res, AccessDeniedException ade)
			throws IOException, ServletException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String url = req.getContextPath() +"/login/access-denied";
		if (ade instanceof MissingCsrfTokenException) {
			logger.info("MissingCsrfTokenException");
		} else if (ade instanceof InvalidCsrfTokenException) {
			logger.info("InvalidCsrfTokenException");
		} else if (authentication != null && ((User) authentication.getPrincipal()).getAuthorities().contains(new SimpleGrantedAuthority("ROLE_VIEW"))) {
			url = req.getContextPath() + "/v";
		} else if (authentication != null && ((User) authentication.getPrincipal()).getAuthorities().contains(new SimpleGrantedAuthority("ROLE_SWAGGER"))) {
			url = req.getContextPath() + "/swagger-ui.html";
		} else {
			authentication.setAuthenticated(false);			
		}
		res.setStatus(HttpStatus.OK.value());
		res.sendRedirect(url);
	}

}
