package com.prography.zone_2_be.domain.alarm.entity;

import com.prography.zone_2_be.domain.user.entity.User;
import com.prography.zone_2_be.global.entity.UpdatableEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(uniqueConstraints = {
	@UniqueConstraint(columnNames = {"user_id", "alarm_type"})
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Alarm extends UpdatableEntity {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@Enumerated(EnumType.STRING)
	@Column(name = "alarm_type", nullable = false)
	private AlarmType alarmType;

	@Column(nullable = false)
	private boolean enabled;

	@Builder
	private Alarm(User user, AlarmType alarmType, boolean enabled) {
		this.user = user;
		this.alarmType = alarmType;
		this.enabled = enabled;
	}

	public static Alarm of(User user, AlarmType alarmType) {
		return Alarm.builder()
			.user(user)
			.alarmType(alarmType)
			.enabled(alarmType.isEssential())
			.build();
	}

	public void updateEnabled(boolean enabled) {
		this.enabled = enabled;
	}
}
