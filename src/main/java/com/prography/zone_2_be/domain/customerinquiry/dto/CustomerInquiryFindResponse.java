package com.prography.zone_2_be.domain.customerinquiry.dto;

import com.prography.zone_2_be.domain.customerinquiry.entity.CustomerInquiry;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CustomerInquiryFindResponse {

	private String title;
	private String content;

	@Builder
	private CustomerInquiryFindResponse(String title, String content) {
		this.title = title;
		this.content = content;
	}

	public static CustomerInquiryFindResponse from(CustomerInquiry customerInquiry) {
		return CustomerInquiryFindResponse.builder()
			.title(customerInquiry.getTitle())
			.content(customerInquiry.getContent())
			.build();
	}
}
