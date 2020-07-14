package me.jiniworld.demo.models.values;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "가게")
@Getter @Setter
public class StoreValue {
	
	@Schema(description = "사용자 id")
	private long userId;
	
	@Schema(description = "이름")
	private String name;
	
	@Schema(description = "업종")
	private String storeBusiness;
}
