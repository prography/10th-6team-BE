package com.prography.zone_2_be.domain.user.device.entity;

import com.prography.zone_2_be.domain.user.entity.User;
import com.prography.zone_2_be.global.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserDevice extends BaseEntity {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(
		name = "user_id",
		nullable = false,
		foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT)
	)
	private User user;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private OsType osType;

	@Column(length = 50)
	private String deviceModel;

	@Builder
	private UserDevice(User user, OsType osType, String deviceModel) {
		this.user = user;
		this.osType = osType;
		this.deviceModel = deviceModel;
	}

	public static UserDevice of(User user, OsType osType, String deviceModel) {
		return UserDevice.builder()
			.user(user)
			.osType(osType)
			.deviceModel(deviceModel)
			.build();
	}
}
