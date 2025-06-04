package com.prography.zone_2_be.domain.notice.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import com.prography.zone_2_be.domain.notice.dto.NoticeResponseDto;
import com.prography.zone_2_be.domain.notice.repository.NoticeRepository;
import com.prography.zone_2_be.global.response.SliceResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NoticeService {

	private final NoticeRepository noticeRepository;

	public SliceResponse<NoticeResponseDto> findAllNotice(Pageable pageable) {
		Slice<NoticeResponseDto> noticeResponse = noticeRepository.findAllByOrderByCreatedAtDesc(pageable)
			.map(NoticeResponseDto::from);
		return SliceResponse.from(noticeResponse);
	}
}
