package com.prography.zone_2_be.domain.auth.dto;

import java.time.LocalDate;

import com.prography.zone_2_be.domain.user.entity.Gender;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Getter;

@Getter
public class UserRegisterRequest {
	@NotNull(message = "registrationId 필수 입력값입니다.")
	public String registrationId;

	@NotNull(message = "termId는 필수입니다.")
	private Long termId;

	@AssertTrue(message = "약관에 동의하지 않으면 진행할 수 없습니다.")
	private boolean agreed;

	//생년월일, 키, 몸무게, 성별
	@NotNull(message = "생년월일은 필수입니다.") // null이 아니어야 함
	@Past(message = "생년월일은 미래의 날짜일 수 없습니다.")
	public LocalDate birth;

	@NotNull
	public Gender gender;

	public Integer height;
	public Integer weight;
}
