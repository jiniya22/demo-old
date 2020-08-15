package me.jiniworld.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {
	
	static {
		System.setProperty("spring.config.location", "classpath:/application.yml,classpath:/demo.yml");
    }
	
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
