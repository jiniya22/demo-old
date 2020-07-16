package me.jiniworld.demo.models.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "에러 Response")
@Getter @Setter
public class ErrorResponse extends BasicResponse {
	
	@Schema(description = "에러 메시지", defaultValue = "")
	private String errorMessage;
	
	@Schema(description = "에러코드", defaultValue = "404", allowableValues = {"404", "500"})
	private String errorCode;
	
	public ErrorResponse(String errorMessage) {
		this.errorMessage = errorMessage;
		this.errorCode = "404";
	}
	public ErrorResponse(String errorMessage, String errorCode) {
		this.errorMessage = errorMessage;
		this.errorCode = errorCode;
	}
}
