package me.jiniworld.demo.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableGlobalMethodSecurity(prePostEnabled=true)
@Configuration
@EnableWebSecurity
public class WebSecurityConfig { //extends WebSecurityConfigurerAdapter {
	
//	@Override
//	public void configure(WebSecurity web) throws Exception {
//		web.ignoring().antMatchers("/static/**");
//	}
//	
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		http.authorizeRequests()
//		.antMatchers("/login", "/api/**").permitAll()
//		.antMatchers("/v", "/v/**").access("hasRole('ROLE_VIEW')")
//		.and()
//			.formLogin()
//		.and()
//			.logout().logoutUrl("/logout")
//			.deleteCookies("JSESSIONID").clearAuthentication(true).invalidateHttpSession(true);
//	}
}
