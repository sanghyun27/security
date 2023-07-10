package com.study.security_sanghyun.web.dto.notice;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class AddNoticeReqDto {
	
	private String noticeTitle;
	private int userCode;
	private String ir1;
	
	private List<MultipartFile> file;
	
}
