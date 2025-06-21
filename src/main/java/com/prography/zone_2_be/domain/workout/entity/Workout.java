package com.prography.zone_2_be.domain.workout.entity;

import java.util.UUID;

import com.prography.zone_2_be.domain.user.entity.User;
import com.prography.zone_2_be.global.entity.BaseEntity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
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
	private double zone2Rate;

	@Column(nullable = false, updatable = false)
	private String uuid;

	@Column
	@Setter
	@Enumerated(EnumType.ORDINAL)
	private Activity activity;

	@Builder
	private Workout(User user, String uuid, long execTime, int kcalUsage, int fatUsage, double zone2Rate, Activity activity) {
		this.user = user;
		this.uuid = uuid;
		this.execTime = execTime;
		this.kcalUsage = kcalUsage;
		this.fatUsage = fatUsage;
		this.zone2Rate = zone2Rate;
		this.activity = activity;
	}

}
