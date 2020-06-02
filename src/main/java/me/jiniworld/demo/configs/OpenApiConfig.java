package me.jiniworld.demo.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Component
public class OpenApiConfig {

	@Bean
	public OpenAPI customOpenAPI(@Value("${springdoc.version}") String appVersion) {
		Info info = new Info().title("Demo API").version(appVersion)
				.description("Spring Boot를 이용한 Demo 웹 애플리케이션 API입니다.")
				.termsOfService("http://swagger.io/terms/")
				.contact(new Contact().name("jini").url("https://blog.jiniworld.me/").email("yeonjini2222@gmail.com"))
				.license(new License().name("Apache License Version 2.0").url("http://www.apache.org/licenses/LICENSE-2.0"));
		
		return new OpenAPI()
				.components(new Components())
				.info(info);
	}
}
