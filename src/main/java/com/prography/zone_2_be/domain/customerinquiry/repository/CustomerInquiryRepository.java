package com.prography.zone_2_be.domain.customerinquiry.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.prography.zone_2_be.domain.customerinquiry.entity.CustomerInquiry;

@Repository
public interface CustomerInquiryRepository extends JpaRepository<CustomerInquiry, Long> {

	Slice<CustomerInquiry> findAllByOrderByIsPinnedDescCreatedAtDesc(Pageable pageable);
}
