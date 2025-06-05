package com.prography.zone_2_be.domain.notice.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.prography.zone_2_be.domain.notice.dto.NoticeFindAllResponse;
import com.prography.zone_2_be.domain.notice.service.NoticeService;
import com.prography.zone_2_be.global.constant.CommonConst;
import com.prography.zone_2_be.global.response.ApiResponse;
import com.prography.zone_2_be.global.response.SliceResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/notice")
@RequiredArgsConstructor
public class NoticeController {

	private final NoticeService noticeService;

	@GetMapping
	public ResponseEntity<ApiResponse<SliceResponse<NoticeFindAllResponse>>> findAllNotice(
		@RequestParam(defaultValue = "0") int pageNumber) {
		Pageable pageable = PageRequest.of(pageNumber, CommonConst.DEFAULT_PAGE_SIZE);
		SliceResponse<NoticeFindAllResponse> response = noticeService.findAllNotice(pageable);

		return ApiResponse.success(response);
	}
}
