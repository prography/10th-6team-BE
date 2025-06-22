package com.prography.zone_2_be.domain.user.dto;

import java.time.LocalDate;

import com.prography.zone_2_be.domain.user.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class UserFindResponse {
	private String uuid;
	private String email;
	private LocalDate birth;
	private int height;
	private int weight;
	private Integer gender;

	public static UserFindResponse from(User user) {
		return UserFindResponse.builder()
			.uuid(user.getUuid())
			.email(user.getEmail())
			.birth(user.getBirth())
			.height(user.getHeight())
			.weight(user.getWeight())
			.gender(user.getGender().getValue())
			.build();
	}
}
