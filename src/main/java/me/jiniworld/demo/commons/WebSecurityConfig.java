package me.jiniworld.demo.commons;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import me.jiniworld.demo.commons.handlers.WebAccessDeniedHandler;
import me.jiniworld.demo.services.SecurityUserService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	private final SecurityUserService securityUserService;
	private final WebAccessDeniedHandler webAccessDeniedHandler;
	
	@Autowired
	public WebSecurityConfig(SecurityUserService securityUserService, WebAccessDeniedHandler webAccessDeniedHandler) {
		this.securityUserService = securityUserService;
		this.webAccessDeniedHandler = webAccessDeniedHandler;
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/static/**");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/", "/login", "/join", "/test/**").permitAll()
			.antMatchers("/v/users").access("hasRole('ROLE_ADMIN')")
			.antMatchers("/v", "/v/**").access("hasRole('ROLE_VIEW')")
			.anyRequest().authenticated()
		.and()
			.formLogin().loginPage("/login").defaultSuccessUrl("/v", true)
			.usernameParameter("email").passwordParameter("password")
		.and()
			.logout().invalidateHttpSession(true).deleteCookies("JSESSIONID")
			.clearAuthentication(true).invalidateHttpSession(true)
		.and().exceptionHandling().accessDeniedHandler(webAccessDeniedHandler)
		.and()
			.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(securityUserService)
		.passwordEncoder(passwordEncoder());
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
}
