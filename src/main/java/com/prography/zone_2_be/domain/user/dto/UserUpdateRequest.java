package com.prography.zone_2_be.domain.user.dto;

import com.prography.zone_2_be.domain.user.entity.Gender;
import jakarta.validation.constraints.NotNull;

public class UserUpdateRequest {
	//생년월일, 키, 몸무게, 성별
	@NotNull
	public long birth;
	@NotNull
	public Integer height;
	@NotNull
	public Integer weight;
	@NotNull
	public Gender gender;
}
