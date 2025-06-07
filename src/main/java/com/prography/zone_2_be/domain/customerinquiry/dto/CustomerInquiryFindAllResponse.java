package com.prography.zone_2_be.domain.customerinquiry.dto;

import com.prography.zone_2_be.domain.customerinquiry.entity.CustomerInquiry;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CustomerInquiryFindAllResponse {

	private Long id;
	private String title;
	private boolean isPinned;

	@Builder
	private CustomerInquiryFindAllResponse(Long id, String title, boolean isPinned) {
		this.id = id;
		this.title = title;
		this.isPinned = isPinned;
	}

	public static CustomerInquiryFindAllResponse from(CustomerInquiry customerInquiry) {
		return CustomerInquiryFindAllResponse.builder()
			.id(customerInquiry.getId())
			.title(customerInquiry.getTitle())
			.isPinned(customerInquiry.isPinned())
			.build();
	}
}
