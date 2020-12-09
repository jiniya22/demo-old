package me.jiniworld.demo.models.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import me.jiniworld.demo.models.simples.UserSimple;

@Schema(description = "회원 Response")
public class UserResponse extends CommonResponse<UserSimple> {

	public UserResponse(UserSimple value) {
		super(value);
	}
	
}
