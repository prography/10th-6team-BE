package com.prography.zone_2_be.domain.workout.entity;

import com.prography.zone_2_be.domain.user.entity.User;
import com.prography.zone_2_be.global.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Workout extends BaseEntity {
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@Column(nullable = false)
	private long execTime;

	@Column(nullable = false)
	private int kcalUsage;

	@Column(nullable = false)
	private int fatUsage;

	@Column(nullable = false)
	private int zone2Rate;

	@Column(nullable = false, updatable = false)
	private String uuid;

	@Column
	@Setter
	@Enumerated(EnumType.ORDINAL)
	private Activity activity;

	@Builder
	private Workout(User user, String uuid, long execTime, int kcalUsage, int fatUsage, int zone2Rate,
		Activity activity) {
		this.user = user;
		this.uuid = uuid;
		this.execTime = execTime;
		this.kcalUsage = kcalUsage;
		this.fatUsage = fatUsage;
		this.zone2Rate = zone2Rate;
		this.activity = activity;
	}

	public static Workout of(User user, String uuid, long execTime, int kcalUsage, int fatUsage, int zone2Rate,
		Activity activity) {
		return Workout.builder()
			.user(user)
			.uuid(uuid)
			.execTime(execTime)
			.kcalUsage(kcalUsage)
			.fatUsage(fatUsage)
			.zone2Rate(zone2Rate)
			.activity(activity)
			.build();
	}

}
