package com.prography.zone_2_be.domain.notice.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.prography.zone_2_be.domain.notice.entity.Notice;

@SpringBootTest
@ActiveProfiles("test")
class NoticeRepositoryTest {
	@Autowired
	private NoticeRepository noticeRepository;

	@Test
	@DisplayName("Id로 공지사항을 조회한다.")
	public void findByIdOrThrow() {
		// given
		Notice notice1 = Notice.of("공지사항 입니다.", "반갑습니다. 첫번째 공지사항입니다.");
		Notice notice2 = Notice.of("공지사항 입니다.", "반갑습니다. 두번째 공지사항입니다.");
		Notice notice3 = Notice.of("공지사항 입니다.", "반갑습니다. 세번째 공지사항입니다.");
		List<Notice> notices = noticeRepository.saveAll(List.of(notice1, notice2, notice3));

		// when
		Notice found = noticeRepository.findByIdOrThrow(notices.get(1).getId());

		// then
		assertThat(found.getId()).isEqualTo(notices.get(1).getId());
		assertThat(found.getTitle()).isEqualTo("공지사항 입니다.");
		assertThat(found.getContent()).isEqualTo("반갑습니다. 두번째 공지사항입니다.");
	}
}
