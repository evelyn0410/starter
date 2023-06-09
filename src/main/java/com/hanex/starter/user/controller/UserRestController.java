package com.hanex.starter.user.controller;

import com.hanex.starter.common.annotation.CustomLog;
import com.hanex.starter.common.api.ApiResponseDto;
import com.hanex.starter.user.service.UserService;
import com.hanex.starter.user.dto.UserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@Tag(name = "사용자 API",description = "JWT 토큰 발급용 사용자")
@RequestMapping("/v1/users")
@RequiredArgsConstructor
@RestController
public class UserRestController {

	private final UserService userService;

	@Operation(summary = "회원가입", description = "User 정보 Insert")
	@PostMapping("/register")
	public ResponseEntity<Void> register(@RequestBody @Valid UserDto.SaveRequest save){
		userService.register(save);
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "로그인 아이디로 사용자 단건 조회", description = "로그인 아이디로 사용자 단건 조회")
	@GetMapping("/{loginId}")
	public ApiResponseDto findByLoginId(@PathVariable String loginId){
		return new ApiResponseDto<>(userService.findByLoginId(loginId));
	}

	@Operation(summary = "사용자 정보 수정",description = "user upate")
	@PutMapping("/{id}")
	public ResponseEntity<Void> update(@PathVariable UUID id,@Valid @RequestBody UserDto.UpdateRequest update){
		userService.update(id,update);
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "회원 탈퇴",description = "Soft Delete > USER STATUS 만 DELETED 로 update")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable UUID id){
		userService.delete(id);
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "로그인",description = "Auth Service 에 JWT 토큰 요청")
	@PostMapping("/login")
	public ApiResponseDto login(@Valid @RequestBody UserDto.LoginRequest request){
		return ApiResponseDto.createOK(userService.login(request));
	}
}
