package com.prography.zone_2_be.domain.workout.entity;

import java.util.UUID;

import com.prography.zone_2_be.domain.user.entity.User;
import com.prography.zone_2_be.global.entity.BaseEntity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Setter;

@Entity
public class Workout extends BaseEntity {
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@Column(nullable = false)
	private long execTime;

	@Column
	private int kcalUsage;

	@Column(name="zone2_usage")
	private int zone2Usage;

	@Column(nullable = false, updatable = false)
	private String uuid;

	@Column
	@Setter
	@Enumerated(EnumType.ORDINAL)
	private Activity activity;

	@Builder
	private Workout(User user, String uuid, long execTime, int kcalUsage, int zone2Usage) {
		this.user = user;
		this.uuid = uuid;
		this.execTime = execTime;
		this.kcalUsage = kcalUsage;
		this.zone2Usage = zone2Usage;
	}

}
