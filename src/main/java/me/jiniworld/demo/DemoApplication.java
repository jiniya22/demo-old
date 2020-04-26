package me.jiniworld.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		System.setProperty("spring.config.location", "classpath:/application.yml,classpath:/demo.yml");
		SpringApplication.run(DemoApplication.class, args);
	}

}
