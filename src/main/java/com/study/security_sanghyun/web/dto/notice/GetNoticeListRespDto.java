package com.study.security_sanghyun.web.dto.notice;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class GetNoticeListRespDto {
	private int noticeCode;
	private String noticeTitle;
	private String userId;
	private String createData;
	private int noticeCount;
	private int totalNoticeCount;
}
