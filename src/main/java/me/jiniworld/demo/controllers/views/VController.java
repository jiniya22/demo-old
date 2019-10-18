package me.jiniworld.demo.controllers.views;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import me.jiniworld.demo.services.UserService;

@RequestMapping("/v")
@Controller
public class VController {
	
	private final UserService userService;
	
	public VController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping("")
	public String main(Model model) {
		model.addAttribute("currentPage", "home");
		return "content/main";
	}
	
	@GetMapping("/users")
	public String selectUsers(Model model, @PageableDefault(page=0, size=5, sort="id", direction = Sort.Direction.DESC) Pageable pageable) {
		model.addAttribute("users", userService.findAll(pageable));
		model.addAttribute("currentPage", "user");
		return "content/user";
	}
}
