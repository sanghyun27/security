package com.study.security_sanghyun.domain.user;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data

public class User {
	// DB에 있는 그대로 적어야함
	private int user_code;
	private String user_name;
	private String user_email;
	private String user_id;
	private String oauth2_id;
	
	@JsonIgnore
	private String user_password;
	
	private String user_roles;
	private String user_provider;
	private String user_profile_img;
	private String user_address;
	private String user_phone;
	private int user_gender;
	
	// user_dtl과 user_mst2에 있는 정보를 다 들고 오기 위해 위에 적음
	
	public List<String> getUserRoles() {
		if(user_roles == null || user_roles.isBlank()) {
			return new ArrayList<String>();
		}
		//	문자열의 공백을 없애고, 단위로 잘라서 배열을 만들고 그 배열을 리스트로 바꾸기
		return Arrays.asList(user_roles.replaceAll(" ", "").split(","));
	}
}











