package me.jiniworld.demo.models.response;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CommonResponse<T> extends BasicResponse {
	private T data;
	
	public CommonResponse(String result, String reason) {
		super(result, reason);
	}
}
