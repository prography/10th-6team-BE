package com.prography.zone_2_be.domain.notice.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import com.prography.zone_2_be.domain.notice.dto.NoticeFindAllResponse;
import com.prography.zone_2_be.domain.notice.dto.NoticeFindResponse;
import com.prography.zone_2_be.domain.notice.entity.Notice;
import com.prography.zone_2_be.domain.notice.repository.NoticeRepository;
import com.prography.zone_2_be.global.response.SliceResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NoticeService {

	private final NoticeRepository noticeRepository;

	public SliceResponse<NoticeFindAllResponse> findAllNotice(Pageable pageable) {
		Slice<NoticeFindAllResponse> noticeResponse = noticeRepository.findAllByOrderByCreatedAtDesc(pageable)
			.map(NoticeFindAllResponse::from);
		return SliceResponse.from(noticeResponse);
	}

	public NoticeFindResponse findNotice(Long noticeId) {
		Notice notice = noticeRepository.findByIdOrThrow(noticeId);
		return NoticeFindResponse.from(notice);
	}
}
