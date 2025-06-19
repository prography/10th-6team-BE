package com.prography.zone_2_be.domain.workout.entity;

public enum Activity {
	Walking(0),
	Jogging(1),
	Cycling(2),
	StairClimbing(3),
	Pilates(4),
	MountainClimbing(5);

	private final int value;

	Activity(int value) {
		this.value = value;
	}

	public int getValue() {
		return this.value;
	}
}
