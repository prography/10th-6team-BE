package com.prography.zone_2_be.domain.workout.entity;

import lombok.Getter;

public enum Activity {
	Walking(0),
	Jogging(1),
	Cycling(2),
	StairClimbing(3),
	Pilates(4),
	Extra(99);

	@Getter
	private final int value;

	Activity(int value) {
		this.value = value;
	}
}
