package me.jiniworld.demo.commons;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import me.jiniworld.demo.commons.handlers.WebAccessDeniedHandler;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	private final WebAccessDeniedHandler webAccessDeniedHandler;
	
	@Autowired
	public WebSecurityConfig(WebAccessDeniedHandler webAccessDeniedHandler) {
		this.webAccessDeniedHandler = webAccessDeniedHandler;
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/static/**");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/", "/login").permitAll()
			.antMatchers("/v/users").access("hasRole('ROLE_ADMIN')")
			.antMatchers("/v", "/v/**").access("hasRole('ROLE_VIEW')")
			.anyRequest().authenticated()
		.and()
			.formLogin().loginPage("/login").defaultSuccessUrl("/v", true)
			.usernameParameter("username").passwordParameter("password")
		.and()
			.logout().invalidateHttpSession(true).deleteCookies("JSESSIONID")
		.and().exceptionHandling().accessDeniedHandler(webAccessDeniedHandler)
		.and()
			.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
	}
}
