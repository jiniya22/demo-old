package me.jiniworld.demo.controllers.views;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import me.jiniworld.demo.services.StoreService;

@RequestMapping("/v/stores")
@RequiredArgsConstructor
@Controller
public class VStoreController {
	
	private final StoreService storeService;
	
	@GetMapping("")
	public String selectStores(Model model, @PageableDefault(page=0, size=5, sort="id", direction = Sort.Direction.DESC) Pageable pageable) {
		model.addAttribute("stores", storeService.findAll(pageable));
		model.addAttribute("currentPage", "store");
		return "content/store";
	}
	
}
