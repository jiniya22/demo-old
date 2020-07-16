package me.jiniworld.demo.models.responses;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "일반 Response")
@Getter @Setter
public class CommonResponse<T> extends BasicResponse {
	@Schema(description = "조회 결과 개수", defaultValue = "0")
	private int count;
	
	@Schema(description = "조회 결과")
	private T data;
	
	public CommonResponse(T data) {
		this.data = data;
		if(data instanceof List) {
			this.count = ((List<?>)data).size();
		} else {
			this.count = 1;
		}
	}
	
}
