package me.jiniworld.demo.models.simples;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

import org.springframework.format.annotation.DateTimeFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserSimple {
	
	private Long id;
	
	@Schema(description = "유형", defaultValue = "0", allowableValues = {"0", "1", "2"})
	private String type;
	
	@Email
	@Schema(description = "이메일", nullable = false, example = "abc@jiniworld.me")
	private String email;
	
	@Schema(description = "이름")
	private String name;
	
	@Pattern(regexp = "[1-2]")
	@Schema(description = "성별", defaultValue = "1", allowableValues = {"1", "2"})
	private String sex;
	
	@DateTimeFormat(pattern = "yyMMdd")
	@Schema(description = "생년월일", example = "yyMMdd", maxLength = 6)
	private String birthDate;
	
	@Schema(description = "전화번호")
	private String phoneNumber;
		
	@Builder
	public UserSimple(Long id, String type, String email, String name, String sex, String birthDate, String phoneNumber) {
		this.id = id;
		this.type = type;
		this.email = email;
		this.name = name;
		this.sex = sex;
		this.birthDate = birthDate;
		this.phoneNumber = phoneNumber;
//		this.password = password;
	}
}
