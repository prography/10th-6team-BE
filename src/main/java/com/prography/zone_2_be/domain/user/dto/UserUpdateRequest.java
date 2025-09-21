package com.prography.zone_2_be.domain.user.dto;

import com.prography.zone_2_be.domain.user.entity.Gender;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;

public class UserUpdateRequest {
	//생년월일, 키, 몸무게, 성별
	@NotNull(message = "생년월일은 필수입니다.") // null이 아니어야 함
	@Past(message = "생년월일은 미래의 날짜일 수 없습니다.")
	public LocalDate birth;
	public Integer height;
	public Integer weight;
	@NotNull
	public Gender gender;
}
