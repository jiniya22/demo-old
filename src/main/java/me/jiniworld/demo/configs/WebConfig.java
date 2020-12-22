package me.jiniworld.demo.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	
	@Bean
	public CommonsRequestLoggingFilter requestLoggingFilter() {
		CommonsRequestLoggingFilter c = new CommonsRequestLoggingFilter();
		c.setIncludeHeaders(true);
		c.setIncludeQueryString(true);
		c.setIncludePayload(true);
		c.setIncludeClientInfo(true);
		c.setMaxPayloadLength(100000);
		return c;
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("robots.txt")
        .addResourceLocations("classpath:/robots.txt");
		
		WebMvcConfigurer.super.addResourceHandlers(registry);
	}
	
}
