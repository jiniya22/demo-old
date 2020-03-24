package me.jiniworld.demo.models.response;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BasicResponse {
	private String result;
	private String reason;
	
	public BasicResponse(String result, String reason) {
		this.result = result;
		this.reason = reason;
	}
}
