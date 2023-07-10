package com.study.security_sanghyun.web.dto.auth;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class UsernameCheckReqDto {
	@NotBlank
	@Size(max = 16, min = 4)
	private String username;	// username은 최소 4글자 최대 16글자 + 공백 포함 불가
}
