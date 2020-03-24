package me.jiniworld.demo.controllers.api.v1;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import me.jiniworld.demo.models.entities.User;
import me.jiniworld.demo.models.response.BasicResponse;
import me.jiniworld.demo.models.response.CommonResponse;
import me.jiniworld.demo.models.values.UserValue;
import me.jiniworld.demo.services.UserService;

@RequestMapping(value = "${demo.api}/users")
@RequiredArgsConstructor
@RestController
public class UserController {
	
	private final UserService userService;
	
	@PostMapping("")
	public CommonResponse<User> save(@RequestBody UserValue value) {
		CommonResponse<User> response = new CommonResponse<>("FAIL", "회원가입 실패");
		
		User user = userService.save(value);
		if(user != null) {
			response.setResult("SUCCESS");
			response.setReason("");
			response.setData(user);
		}
		
		return response;
	}
	
	@GetMapping("/{id}")
	public CommonResponse<User> findById(@PathVariable("id") long id) {
		CommonResponse<User> response = new CommonResponse<>("FAIL", "일치하는 회원 정보가 없습니다. 사용자 id를 확인해주세요.");
		
		userService.findById(id).ifPresent(user -> {
			response.setResult("SUCCESS");
			response.setReason("");
			response.setData(user);
		});
		
		return response;
	}
	
	@GetMapping("/{id}/2")
	public ResponseEntity<CommonResponse<User>> findById2(@PathVariable("id") long id) {
		CommonResponse<User> body = new CommonResponse<>("FAIL", "일치하는 회원 정보가 없습니다. 사용자 id를 확인해주세요.");
		
		Optional<User> oUser = userService.findById(id);
		if(oUser.isPresent()) {
			body.setResult("SUCCESS");
			body.setReason("");
			body.setData(oUser.get());
		} else {
			return ResponseEntity.badRequest().body(body);
		}
		return ResponseEntity.ok().body(body);
	}
	
	@PatchMapping("/{id}")
	public CommonResponse<User> patch(@PathVariable("id") long id, @RequestBody UserValue value) {
		CommonResponse<User> response = new CommonResponse<>("FAIL", "일치하는 회원 정보가 없습니다. 사용자 id를 확인해주세요.");
		
		if(userService.patch(id, value) > 0) {
			response.setResult("SUCCESS");
			response.setReason("");
			userService.findById(id).ifPresent(user -> {
				response.setData(user);				
			});
		} 
		
		return response;
	}
	
	@DeleteMapping("/{id}")
	public BasicResponse delete(@PathVariable("id") User user) {
		BasicResponse response = new BasicResponse("FAIL", "일치하는 회원 정보가 없습니다. 사용자 id를 확인해주세요.");
		
		if(user != null && !user.isDel()) {
			userService.delete(user);
			response.setResult("SUCCESS");
			response.setReason("");
		} 
		
		return response;
	}
	
}
