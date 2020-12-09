package me.jiniworld.demo.controllers.api.v1;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import me.jiniworld.demo.models.responses.BasicResponse;
import me.jiniworld.demo.models.responses.ErrorResponse;
import me.jiniworld.demo.models.responses.UserResponse;
import me.jiniworld.demo.models.simples.UserSimple;
import me.jiniworld.demo.models.values.UserValue;
import me.jiniworld.demo.services.UserService;

@Tag(name = "user", description = "사용자 API")
@RequestMapping(value = "${demo.api}/users")
@RequiredArgsConstructor
@RestController
public class UserController {
	
	private final UserService userService;
	
	@PostMapping("")
	@Operation(summary = "회원 가입", 
	responses = {
			@ApiResponse(responseCode = "201", description = "회원 리소스 생성 성공", content = @Content(schema = @Schema(hidden = true))),
			@ApiResponse(responseCode = "500", description = "내부 서버 오류", content = @Content(schema = @Schema(implementation = ErrorResponse.class))) }	)
	public ResponseEntity<? extends BasicResponse> save(@RequestBody @Valid final UserValue value) {
		UserSimple user = userService.join(value);
		if(user == null) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ErrorResponse("회원 가입 실패", "500"));
		}
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/{id}")
	@Operation(summary = "회원 조회", description = "id를 이용하여 user 레코드를 조회합니다.", responses = {
			@ApiResponse(responseCode = "200", description = "회원 조회 성공", content = @Content(schema = @Schema(implementation = UserResponse.class))),
			@ApiResponse(responseCode = "404", description = "존재하지 않는 리소스 접근", content = @Content(schema = @Schema(implementation = ErrorResponse.class))) })
	public ResponseEntity<? extends BasicResponse> select(
			@Parameter(name = "id", description = "user 의 id", in = ParameterIn.PATH) @PathVariable("id") long id) {
		UserSimple value = userService.findById(id);
		if(value == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ErrorResponse("일치하는 회원 정보가 없습니다. 사용자 id를 확인해주세요."));
		}
		return ResponseEntity.ok().body(new UserResponse(value));
	}
	
	@PatchMapping("/{id}")
	@Operation(summary = "회원 수정", responses = {
			@ApiResponse(responseCode = "204", description = "컨텐츠 없음", content = @Content(schema = @Schema(hidden = true))),
			@ApiResponse(responseCode = "404", description = "존재하지 않는 리소스 접근", content = @Content(schema = @Schema(implementation = ErrorResponse.class))) })
	public ResponseEntity<? extends BasicResponse> patch(
			@Parameter(description = "user 의 id") @PathVariable("id") long id, @RequestBody @Valid final UserValue value) {
		if(!userService.patch(id, value)) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ErrorResponse("일치하는 회원 정보가 없습니다. 사용자 id를 확인해주세요."));
		}
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{id}")
	@Operation(summary = "회원 삭제", responses = {
			@ApiResponse(responseCode = "204", description = "컨텐츠 없음", content = @Content(schema = @Schema(hidden = true))),
			@ApiResponse(responseCode = "404", description = "존재하지 않는 리소스 접근", content = @Content(schema = @Schema(implementation = ErrorResponse.class))) })
	public ResponseEntity<? extends BasicResponse> delete(
			@Parameter(description = "user 의 id") @PathVariable("id") long id) {
		if(!userService.delete(id)) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ErrorResponse("일치하는 회원 정보가 없습니다. 사용자 id를 확인해주세요."));
		}
		return ResponseEntity.noContent().build();
	}
	
	
}
