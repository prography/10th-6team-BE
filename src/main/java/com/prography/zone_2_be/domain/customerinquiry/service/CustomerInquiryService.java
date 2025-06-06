package com.prography.zone_2_be.domain.customerinquiry.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import com.prography.zone_2_be.domain.customerinquiry.dto.CustomerInquiryFindAllResponse;
import com.prography.zone_2_be.domain.customerinquiry.dto.CustomerInquiryFindResponse;
import com.prography.zone_2_be.domain.customerinquiry.entity.CustomerInquiry;
import com.prography.zone_2_be.domain.customerinquiry.repository.CustomerInquiryRepository;
import com.prography.zone_2_be.global.response.SliceResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerInquiryService {

	private final CustomerInquiryRepository customerInquiryRepository;

	public SliceResponse<CustomerInquiryFindAllResponse> findAllCustomerInquiry(Pageable pageable) {
		Slice<CustomerInquiryFindAllResponse> slice = customerInquiryRepository.findAllByOrderByIsPinnedDescCreatedAtDesc(
				pageable)
			.map(CustomerInquiryFindAllResponse::from);
		return SliceResponse.from(slice);
	}

	public CustomerInquiryFindResponse findCustomerInquiry(Long customerInquiryId) {
		CustomerInquiry customerInquiry = customerInquiryRepository.findByIdOrThrow(
			customerInquiryId);
		return CustomerInquiryFindResponse.from(customerInquiry);
	}
}
