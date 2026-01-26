package com.prography.zone_2_be.domain.auth.dto;

import java.time.LocalDate;
import java.util.List;

import com.prography.zone_2_be.domain.term.agreement.dto.TermAgreementSaveRequest;
import com.prography.zone_2_be.domain.user.device.dto.request.UserDeviceSaveRequest;
import com.prography.zone_2_be.domain.user.entity.Gender;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserRegisterRequest {
	@NotNull(message = "registrationId 필수 입력값입니다.")
	public String registrationId;

	//생년월일, 키, 몸무게, 성별
	@NotNull(message = "생년월일은 필수입니다.") // null이 아니어야 함
	@Past(message = "생년월일은 미래의 날짜일 수 없습니다.")
	public LocalDate birth;

	@NotNull
	public Gender gender;

	public Integer height;
	public Integer weight;

	@Valid
	private List<TermAgreementSaveRequest> termAgreementSaveRequests;

	private UserDeviceSaveRequest userDeviceSaveRequest;

}
