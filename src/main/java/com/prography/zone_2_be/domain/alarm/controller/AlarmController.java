package com.prography.zone_2_be.domain.alarm.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prography.zone_2_be.domain.alarm.service.AlarmService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/alarm")
@Slf4j
public class AlarmController {

	private final AlarmService alarmService;

}
