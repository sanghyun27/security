package com.study.security_sanghyun.web.dto.notice;

import java.util.List;
import java.util.Map;

import lombok.Builder;
import lombok.Data;

@Builder
@Data

public class GetNoticeRespDto {
	private int noticeCode;
	private String noticeTitle;
	private int userCode;
	private String userId;
	private String createDate;
	private int noticeCount;
	private String noticeContent;
	private List<Map<String, Object>> downloadFiles;
}
