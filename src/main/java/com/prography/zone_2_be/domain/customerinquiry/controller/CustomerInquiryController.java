package com.prography.zone_2_be.domain.customerinquiry.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.prography.zone_2_be.domain.customerinquiry.dto.CustomerInquiryFindAllResponse;
import com.prography.zone_2_be.domain.customerinquiry.service.CustomerInquiryService;
import com.prography.zone_2_be.global.constant.CommonConst;
import com.prography.zone_2_be.global.response.ApiResponse;
import com.prography.zone_2_be.global.response.SliceResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/customer-inquiry")
@RequiredArgsConstructor
public class CustomerInquiryController {

	private final CustomerInquiryService customerInquiryService;

	@GetMapping
	public ResponseEntity<ApiResponse<SliceResponse<CustomerInquiryFindAllResponse>>> findAllCustomerInquiry(
		@RequestParam(defaultValue = "0") int pageNumber) {
		Pageable pageable = PageRequest.of(pageNumber, CommonConst.DEFAULT_PAGE_SIZE);
		SliceResponse<CustomerInquiryFindAllResponse> response = customerInquiryService.findAllCustomerInquiry(
			pageable);

		return ApiResponse.success(response);
	}
}
