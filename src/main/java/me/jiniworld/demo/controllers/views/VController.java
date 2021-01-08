package me.jiniworld.demo.controllers.views;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@RequestMapping("/v")
@RequiredArgsConstructor
@Controller
public class VController {
	
	@GetMapping("")
	public String main(Model model) {
		model.addAttribute("currentPage", "home");
		return "content/main";
	}
}
