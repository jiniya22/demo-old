package me.jiniworld.demo.models.values;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "사용자")
@Getter @Setter
public class UserValue {
	@Schema(description = "유형", defaultValue = "0", allowableValues = {"0", "1", "2"})
	private String type;
	
	@Schema(description = "이메일", nullable = false, example = "abc@jiniworld.me")
	private String email;
	
	@Schema(description = "이름")
	private String name;
	
	@Schema(description = "성별", defaultValue = "1", allowableValues = {"1", "2"})
	private String sex;
	
	@Schema(description = "생년월일", example = "yyMMdd")
	private String birthDate;
	
	@Schema(description = "전화번호")
	private String phoneNumber;
	
	@Schema(description = "비밀번호")
	private String password;
	
}
