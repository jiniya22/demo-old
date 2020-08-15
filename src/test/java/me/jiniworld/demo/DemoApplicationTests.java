package me.jiniworld.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {
	
	static {
		System.setProperty("spring.config.location", "classpath:/application.yml,classpath:/demo.yml");
    }
	
	@Test
	public void contextLoads() {
	}

}
