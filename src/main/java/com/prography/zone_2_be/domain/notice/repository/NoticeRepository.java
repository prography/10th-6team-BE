package com.prography.zone_2_be.domain.notice.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.prography.zone_2_be.domain.notice.entity.Notice;
import com.prography.zone_2_be.global.error.ErrorCode;
import com.prography.zone_2_be.global.exception.CustomException;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {

	//default 메서드
	default Notice findByIdOrThrow(Long noticeId) {
		return findById(noticeId)
			.orElseThrow(() -> new CustomException(ErrorCode.NOTICE_NOT_FOUND));
	}

	Slice<Notice> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
