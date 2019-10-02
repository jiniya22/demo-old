package me.jiniworld.demo.controllers.views;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/v")
@Controller
public class VController {
	
	@GetMapping("")
	public String main() {
		return "content/main";
	}
}
