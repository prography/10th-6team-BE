package com.prography.zone_2_be.domain.term.agreement.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TermAgreementSaveRequest {

	@NotNull(message = "termId는 필수입니다.")
	private Long termId;

	@AssertTrue(message = "약관에 동의하지 않으면 진행할 수 없습니다.")
	private boolean agreed;
}
