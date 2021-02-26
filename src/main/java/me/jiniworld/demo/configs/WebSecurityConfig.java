package me.jiniworld.demo.configs;

import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.RequestMatcher;

import lombok.RequiredArgsConstructor;
import me.jiniworld.demo.configs.handlers.WebAccessDeniedHandler;
import me.jiniworld.demo.services.SecurityUserService;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	private final SecurityUserService securityUserService;
	private final WebAccessDeniedHandler webAccessDeniedHandler;
		
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/static/**", "/api-docs", "/api-docs/**", "/robots.txt");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/", "/login", "/join", "/api/v1/**", "/test/**").permitAll()
			.antMatchers("/v/users").hasRole("ADMIN")
			.antMatchers("/v", "/v/**").hasRole("VIEW")
			.antMatchers("/swagger-ui.html", "/swagger-ui/**").hasRole("VIEW")
			.anyRequest().authenticated()
		.and()
			.formLogin().loginPage("/login").defaultSuccessUrl("/v", true)
			.usernameParameter("email").passwordParameter("password")
		.and()
			.logout().invalidateHttpSession(true).deleteCookies("JSESSIONID")
		.and().exceptionHandling().accessDeniedHandler(webAccessDeniedHandler)		
		.and()
			.authenticationProvider(authenticationProvider())
		.csrf()
			.requireCsrfProtectionMatcher(new CsrfRequireMatcher())
			.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(securityUserService);
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}	
		
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	static class CsrfRequireMatcher implements RequestMatcher {
	    private static final Pattern ALLOWED_METHODS = Pattern.compile("^(GET|HEAD|TRACE|OPTIONS)$");
	    
	    @Override
	    public boolean matches(HttpServletRequest request) {
	        if (ALLOWED_METHODS.matcher(request.getMethod()).matches())
	        	return false;
	        
	        final String referer = request.getHeader("Referer"), userAgent = request.getHeader("User-Agent");
	        if (referer != null && referer.contains("/swagger-ui")) {
	            return false;
	        } else if(userAgent != null && userAgent.contains("HTTPie")) {
	            return false;
	        }
	        return true;
	    }
	}
}
