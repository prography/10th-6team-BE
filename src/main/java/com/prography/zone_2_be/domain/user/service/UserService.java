package com.prography.zone_2_be.domain.user.service;

import org.springframework.stereotype.Service;

import com.prography.zone_2_be.domain.user.dto.UserFindResponse;
import com.prography.zone_2_be.domain.user.dto.UserUpdateRequest;
import com.prography.zone_2_be.domain.user.entity.User;
import com.prography.zone_2_be.domain.user.exception.UserNotFoundException;
import com.prography.zone_2_be.domain.user.repository.UserRepository;
import com.prography.zone_2_be.global.utils.JwtUtil;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
	private final UserRepository userRepository;
	private final JwtUtil jwtUtil;

	public User findUserByUuid(String uuid) throws UserNotFoundException {
		return userRepository.findByUuid(uuid).orElseThrow(UserNotFoundException::new);
	}

	public UserFindResponse findUser() {
		return UserFindResponse.from(
			userRepository.findByUuid(jwtUtil.getUuid()).orElseThrow(UserNotFoundException::new));
	}

	@Transactional
	public void updateUser(UserUpdateRequest dto) {
		String uuid = jwtUtil.getUuid();
		User user = userRepository.findByUuid(uuid).orElseThrow(UserNotFoundException::new);
		user.updateUserInfo(dto);
	}
}
