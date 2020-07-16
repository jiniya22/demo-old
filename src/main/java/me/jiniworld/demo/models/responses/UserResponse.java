package me.jiniworld.demo.models.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import me.jiniworld.demo.models.entities.User;

@Schema(description = "회원 Response")
public class UserResponse extends CommonResponse<User> {

	public UserResponse(User data) {
		super(data);
	}
	
}
