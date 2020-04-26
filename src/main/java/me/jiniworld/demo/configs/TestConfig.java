package me.jiniworld.demo.configs;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Setter;

@Setter
@ConfigurationProperties(prefix = "demo.test")
@Configuration
public class TestConfig {
	
	private String userId;
	private String name;
	private String password;
	private List<String> sites;
	private Option option;
	
	@Setter
	public static class Option {
        private boolean admin;
        private boolean view;
        
		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("[admin=").append(admin).append(", view=").append(view).append("]");
			return builder.toString();
		}
		
    }

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TestConfig [userId=").append(userId).append(", name=").append(name).append(", password=")
				.append(password).append(", sites=").append(sites).append(", option=").append(option).append("]");
		return builder.toString();
	}
	
}