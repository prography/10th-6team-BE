package com.prography.zone_2_be.domain.user.service;

import java.time.Instant;

import org.springframework.stereotype.Service;

import com.prography.zone_2_be.domain.user.dto.UserFindResponse;
import com.prography.zone_2_be.domain.user.dto.UserUpdateRequest;
import com.prography.zone_2_be.domain.user.entity.User;
import com.prography.zone_2_be.domain.user.repository.UserRepository;
import com.prography.zone_2_be.global.error.ErrorCode;
import com.prography.zone_2_be.global.exception.CustomException;
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

	public User findUserByUuid(String uuid) throws CustomException {
		return userRepository.findByUuid(uuid).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
	}

	public UserFindResponse findUser() {
		return UserFindResponse.from(JwtUtil.getUser());
	}

	@Transactional
	public void updateUser(UserUpdateRequest dto) {
		// JwtUtil에서 현재 사용자의 UserDetails (principal)를 가져옴
		User currentUserPrincipal = JwtUtil.getUser();

		// 2. 현재 사용자 ID를 사용하여 DB에서 User 엔티티를 다시 조회
		//    이렇게 조회된 엔티티는 현재 트랜잭션의 영속성 컨텍스트에 의해 관리
		User user = findUserByUuid(currentUserPrincipal.getUuid());

		user.updateUserInfo(dto); // 이 변경은 트랜잭션 커밋 시점에 자동으로 DB에 반영됨
	}

	public void deleteUser() {
		User user = JwtUtil.getUser();
		userRepository.delete(user);
		log.info("사용자 {} 삭제됨", user.getUuid());
	}

	public long countByCreatedAtBetween(Instant start, Instant end) {
		return userRepository.countByCreatedAtBetween(start, end);
	}
}
