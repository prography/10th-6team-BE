package com.prography.zone_2_be.domain.customerinquiry.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import com.prography.zone_2_be.domain.customerinquiry.entity.CustomerInquiry;
import com.prography.zone_2_be.global.constant.CommonConst;

@SpringBootTest
@ActiveProfiles("test")
class CustomerInquiryRepositoryTest {

	@Autowired
	private CustomerInquiryRepository customerInquiryRepository;

	@Test
	@DisplayName("Pinned=true 그룹만 추출해서 createdAt 내림차순인지 검증")
	void pinnedGroupOrdering() {
		List<CustomerInquiry> all = customerInquiryRepository
			.findAllByOrderByIsPinnedDescCreatedAtDesc(PageRequest.of(0, CommonConst.DEFAULT_PAGE_SIZE))
			.getContent();

		List<CustomerInquiry> pinned = all.stream()
			.filter(CustomerInquiry::isPinned)
			.toList();

		for (int i = 0; i < pinned.size() - 1; i++) {
			Long current = pinned.get(i).getCreatedAt();
			Long next = pinned.get(i + 1).getCreatedAt();
			assertThat(current)
				.as("Pinned 그룹 내에서 createdAt 내림차순이 유지되어야 한다")
				.isGreaterThanOrEqualTo(next);
		}
	}

	@Test
	@DisplayName("Pinned=false 그룹만 추출해서 createdAt 내림차순인지 검증")
	void normalGroupOrdering() {
		List<CustomerInquiry> all = customerInquiryRepository
			.findAllByOrderByIsPinnedDescCreatedAtDesc(PageRequest.of(0, CommonConst.DEFAULT_PAGE_SIZE))
			.getContent();

		List<CustomerInquiry> normal = all.stream()
			.filter(ci -> !ci.isPinned())
			.toList();

		for (int i = 0; i < normal.size() - 1; i++) {
			Long current = normal.get(i).getCreatedAt();
			Long next = normal.get(i + 1).getCreatedAt();
			assertThat(current)
				.as("Normal 그룹 내에서 createdAt 내림차순이 유지되어야 한다")
				.isGreaterThanOrEqualTo(next);
		}
	}

	@Test
	@DisplayName("전체 리스트에서 pinned 그룹이 앞쪽에 몰려 있는지 확인")
	void pinnedGroupIsFront() {
		List<CustomerInquiry> all = customerInquiryRepository
			.findAllByOrderByIsPinnedDescCreatedAtDesc(PageRequest.of(0, CommonConst.DEFAULT_PAGE_SIZE))
			.getContent();

		List<CustomerInquiry> pinned = all.stream()
			.filter(CustomerInquiry::isPinned)
			.toList();

		for (int i = 0; i < pinned.size(); i++) {
			assertThat(all.get(i).isPinned())
				.as("첫 %d개 항목은 모두 pinned=true여야 한다", pinned.size())
				.isTrue();
		}
		for (int i = pinned.size(); i < all.size(); i++) {
			assertThat(all.get(i).isPinned())
				.as("pinned 그룹이 끝난 이후에는 모두 pinned=false여야 한다")
				.isFalse();
		}
	}
}
