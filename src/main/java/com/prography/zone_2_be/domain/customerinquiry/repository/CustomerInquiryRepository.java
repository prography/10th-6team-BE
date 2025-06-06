package com.prography.zone_2_be.domain.customerinquiry.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.prography.zone_2_be.domain.customerinquiry.entity.CustomerInquiry;
import com.prography.zone_2_be.global.error.ErrorCode;
import com.prography.zone_2_be.global.exception.CustomException;

@Repository
public interface CustomerInquiryRepository extends JpaRepository<CustomerInquiry, Long> {

	//default 메서드
	default CustomerInquiry findByIdOrThrow(Long customerInquiryId) {
		return findById(customerInquiryId)
			.orElseThrow(() -> new CustomException(ErrorCode.DEFAULT_ERROR));
	}

	Slice<CustomerInquiry> findAllByOrderByIsPinnedDescCreatedAtDesc(Pageable pageable);
}
