package me.jiniworld.demo.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import me.jiniworld.demo.models.responses.ErrorResponse;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@ApiResponses(value = {
		@ApiResponse(responseCode = "204", description = "컨텐츠 없음", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = Void.class)))}),
		@ApiResponse(responseCode = "404", description = "존재하지 않는 리소스 접근", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class)))})
  })
public @interface DemoApiResponses {

}
