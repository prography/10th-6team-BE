package com.prography.zone_2_be.domain.notice.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.prography.zone_2_be.domain.notice.entity.Notice;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {

	Slice<Notice> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
