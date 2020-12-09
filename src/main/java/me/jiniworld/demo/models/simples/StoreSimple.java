package me.jiniworld.demo.models.simples;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class StoreSimple {
	
	private Long id;
	
	@Schema(description = "사용자 id")
	private Long userId;
	
	@Schema(description = "이름")
	private String name;
	
	@Schema(description = "업종")
	private String storeBusiness;
	
	@Builder
	public StoreSimple(Long id, Long userId, String name, String storeBusiness) {
		this.id = id;
		this.userId = userId;
		this.name = name;
		this.storeBusiness = storeBusiness;
	}
}
