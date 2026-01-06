package com.prography.zone_2_be.domain.alarm.service;

import org.springframework.stereotype.Service;

import com.prography.zone_2_be.domain.alarm.repository.AlarmRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AlarmService {

	private final AlarmRepository alarmRepository;

}
