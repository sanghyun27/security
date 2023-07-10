package com.study.security_sanghyun.web.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nimbusds.jose.util.StandardCharset;
import com.study.security_sanghyun.web.dto.CMRespDto;
import com.study.security_sanghyun.web.dto.notice.AddNoticeReqDto;
import com.study.security_sanghyun.web.dto.notice.GetNoticeListRespDto;
import com.study.security_sanghyun.web.dto.notice.GetNoticeRespDto;
import com.study.security_sanghyun.web.dto.notice.NoticeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/notice")
@RequiredArgsConstructor
public class NoticeRestController {
	
	private final NoticeService noticeService;
	
	@Value("${file.path}")
	private String filePath;
	
	@PostMapping("")
	public ResponseEntity<?> addNotice(AddNoticeReqDto addNoticeReqDto) {
//		log.info(">>> {}:", addNoticeReqDto);
//		log.info(">>> fileName: {}", addNoticeReqDto.getFile().get(0).getOriginalFilename());
//		log.info(">>> filepath: {}", filepath);
		
		int noticeCode = 0;
		
		try {
			noticeCode = noticeService.addNotice(addNoticeReqDto);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body(new CMRespDto<>(-1, "failed", noticeCode));

		}
		return ResponseEntity.ok().body(new CMRespDto<>(1, "complete creation", noticeCode));
	}
	
	//	게시물 그냥 보는거
	@GetMapping("/{noticeCode}")
	public ResponseEntity<?> getNotice(@PathVariable int noticeCode) {
		
		GetNoticeRespDto getNoticeRespDto = null;
		try {
			getNoticeRespDto = noticeService.getNotice(null, noticeCode);
			if(getNoticeRespDto == null) {
				return  ResponseEntity.badRequest().body(new CMRespDto<>(-1, "database error", null));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body(new CMRespDto<>(-1, "database error", null));
		}
		return ResponseEntity.ok().body(new CMRespDto<>(1, "success", getNoticeRespDto));
	}
	
	
	//	게시글 이전 다음 눌렀을 때
	@GetMapping("/{flag}/{noticeCode}")
	public ResponseEntity<?> getNotice(@PathVariable String flag, @PathVariable int noticeCode) {
		GetNoticeRespDto getNoticeRespDto = null;

		if(flag.equals("prev") || flag.equals("next")) {
			try {
				getNoticeRespDto = noticeService.getNotice(null, noticeCode);
				if(getNoticeRespDto == null) {
					return ResponseEntity.badRequest().body(new CMRespDto<>(-1, "database error", null));
				}
			} catch (Exception e) {
				e.printStackTrace();
				return ResponseEntity.internalServerError().body(new CMRespDto<>(-1, "database error", null));
			}
		}else {
			return ResponseEntity.ok().body(new CMRespDto<>(1, "request failed", null));

		}
		return ResponseEntity.ok().body(new CMRespDto<>(1, "success", getNoticeRespDto));

	}
	
	@GetMapping("/file/download/{fileName}")
	public ResponseEntity<?> downloadFile(@PathVariable String fileName) throws IOException {
		Path path = Paths.get(filePath + "notice/" + fileName);	// 다운로드 하고자하는 파일경로
		String contentType = Files.probeContentType(path);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentDisposition(ContentDisposition.builder("attachment")
														.filename(fileName, StandardCharset.UTF_8)
														.build());
		headers.add(HttpHeaders.CONTENT_TYPE, contentType);
		Resource resource = new InputStreamResource(Files.newInputStream(path));
		
		return ResponseEntity.ok().headers(headers).body(resource);
		
	}
	
	@GetMapping("/list/{page}")
	public ResponseEntity<?> getnNoticeList(@PathVariable int page, @RequestParam String searchFlag, @RequestParam String searchValue) {
		
		List<GetNoticeListRespDto> listDto = null;
		
		try {
			listDto = noticeService.getNoticeList(page, searchFlag, searchValue);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body(new CMRespDto<>(-1, "database error", listDto));

		}
		
		return ResponseEntity.ok().body(new CMRespDto<>(1, "success", listDto));
	}
	
	
	
	
	
}
