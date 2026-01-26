package com.prography.zone_2_be.domain.user.device.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prography.zone_2_be.domain.user.device.dto.request.UserDeviceSaveRequest;
import com.prography.zone_2_be.domain.user.device.entity.UserDevice;
import com.prography.zone_2_be.domain.user.device.repository.UserDeviceRepository;
import com.prography.zone_2_be.domain.user.entity.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDeviceService {

	private final UserDeviceRepository userDeviceRepository;

	@Transactional
	public UserDevice save(User user, UserDeviceSaveRequest request) {

		UserDevice userDevice = UserDevice.of(user, request.getOsType(), request.getDeviceModel());
		
		return userDeviceRepository.save(userDevice);
	}

}
