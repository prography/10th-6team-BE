package com.prography.zone_2_be.domain.notice.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.prography.zone_2_be.domain.notice.dto.NoticeFindResponse;
import com.prography.zone_2_be.domain.notice.entity.Notice;
import com.prography.zone_2_be.domain.notice.repository.NoticeRepository;

@SpringBootTest
@ActiveProfiles("test")
class NoticeServiceTest {

	@Autowired
	private NoticeService noticeService;

	@Autowired
	private NoticeRepository noticeRepository;

	@Test
	@DisplayName("파라미터로 받은 ID를 그대로 repository에 넘기고, 반환된 엔티티가 DTO로 매핑되는지")
	public void findNotice_shouldCallRepositoryAndReturnDto() {
		// given
		Notice save = noticeRepository.save(Notice.of("제목", "내용"));
		Long id = save.getId();

		// when
		NoticeFindResponse response = noticeService.findNotice(id);

		// then
		assertThat(response.getTitle()).isEqualTo(save.getTitle());
		assertThat(response.getContent()).isEqualTo(save.getContent());
	}
}
