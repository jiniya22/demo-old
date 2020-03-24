package me.jiniworld.demo.configs;

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
		web.ignoring().antMatchers("/static/**");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/", "/login", "/join", "/api/v1/**").permitAll()
			.antMatchers("/v/users").hasRole("ADMIN")
			.antMatchers("/v", "/v/**").hasRole("VIEW")
			.anyRequest().authenticated()
		.and()
			.formLogin().loginPage("/login").defaultSuccessUrl("/v", true)
			.usernameParameter("email").passwordParameter("password")
		.and()
			.logout().invalidateHttpSession(true).deleteCookies("JSESSIONID")
		.and().exceptionHandling().accessDeniedHandler(webAccessDeniedHandler)		
		.and()
			.authenticationProvider(authenticationProvider())
			.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
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
	
}