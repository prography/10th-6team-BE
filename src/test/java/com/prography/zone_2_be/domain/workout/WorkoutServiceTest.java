package com.prography.zone_2_be.domain.workout;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import com.prography.zone_2_be.domain.user.entity.User;
import com.prography.zone_2_be.domain.user.repository.UserRepository;
import com.prography.zone_2_be.domain.workout.dto.WorkoutGetHistoryResponse;
import com.prography.zone_2_be.domain.workout.entity.Activity;
import com.prography.zone_2_be.domain.workout.entity.Workout;
import com.prography.zone_2_be.domain.workout.repository.WorkoutRepository;
import com.prography.zone_2_be.domain.workout.service.WorkoutService;
import com.prography.zone_2_be.global.utils.JwtUtil;

@SpringBootTest
// @ActiveProfiles("main")
public class WorkoutServiceTest {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private WorkoutRepository workoutRepository;

	@Autowired
	private WorkoutService workoutService;

	@Autowired
	private JwtUtil jwtUtil;

	/**
	 * 이 테스트를 진행하기 위해서는 created_at 컬럼의 CreatedDate 어노테이션을 제거 및 Setter 어노테이션을 추가해야 합니다.
	 */
	@Test
	public void createDummyDataWithAuthKey() {
		// 1. 기준이 될 사용자를 한 번 조회합니다.
		String oauth2Key = "key";

		createDummyData(oauth2Key);
	}

	@Test
	public void createDummyDataWithJWT() {
		String token = "eyJhbGciOiJIUzM4NCJ9.eyJqdGkiOiIxYTQ5OThhOS03YjczLTRhOTItYjkyOC1kZmZiMTMyYWQ5MjUiLCJzdWIiOiJrZXkiLCJpYXQiOjE3NTI5OTUyNzQsImV4cCI6MTc1Mjk5ODg3NH0.7-696ZgV6x_Dr3MVlmDL1398x11XaxHimf3RiPQNp5nYKuHXvBHKQNKbU3aSrzEn";

		// createDummyData(jwtUtil.getOAuth2Key(token));

	}

	@Test
	@DisplayName("운동 기록 조회 - 정상 케이스")
	public void getWorkoutHistory_Success() {
		// Given
		// 테스트용 사용자 생성 및 저장
		User testUser = userRepository.findById(1L).orElseThrow();

		// SecurityContext에 인증 정보 설정
		UsernamePasswordAuthenticationToken authentication =
			new UsernamePasswordAuthenticationToken(testUser, null, testUser.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);

		LocalDateTime startDate = LocalDateTime.of(2025, 10, 1, 0, 0, 0);
		long startTime = startDate.atZone(ZoneId.systemDefault()).toEpochSecond();
		LocalDateTime endDate = LocalDateTime.of(2025, 10, 21, 0, 0, 0);
		long endTime = endDate.atZone(ZoneId.systemDefault()).toEpochSecond();

		// When
		WorkoutGetHistoryResponse response = workoutService.getWorkoutHistory(startTime, endTime, 0, 10);

		// Then
		assertThat(response).isNotNull();
		assertThat(response.getHistories()).isNotEmpty();
		System.out.println("Total Exec Time: " + response.getTotalExecTime());
		System.out.println("Total Kcal Usage: " + response.getTotalKcalUsage());
		System.out.println("Total Fat Usage: " + response.getTotalZone2FatUsage());
		System.out.println("Histories Size: " + response.getHistories().size());

		// SecurityContext 정리
		SecurityContextHolder.clearContext();
	}

	public void createDummyData(String oauth2Key) {
		User user = userRepository.findByOauth2Key(oauth2Key).get();

		// 2. 랜덤으로 선택할 운동 종류 목록을 미리 정의합니다.
		// 2. 랜덤 선택 대상이 될 Activity 목록을 미리 정의합니다.
		//    특별한 케이스인 'Extra'는 제외하고 일반적인 운동만 포함시킵니다.
		List<Activity> normalActivities = Arrays.stream(Activity.values())
			.filter(activity -> activity != Activity.Extra)
			.toList();

		// 3. 10개의 더미 데이터를 생성하는 루프입니다.
		for (int i = 0; i < 10; i++) {
			// 4. 문맥에 맞는 랜덤 값을 생성합니다.
			int kcalUsage = ThreadLocalRandom.current().nextInt(100, 1001); // 100 ~ 1000 kcal
			int execTime = ThreadLocalRandom.current().nextInt(600, 7201); // 10분(600초) ~ 2시간(7200초)
			int zone2Rate = ThreadLocalRandom.current().nextInt(30, 91); // Zone2 비율 30% ~ 90%
			// 정의된 활동 목록에서 랜덤으로 하나를 선택합니다.
			Activity activity = normalActivities.get(ThreadLocalRandom.current().nextInt(normalActivities.size()));

			// 5. 생성된 랜덤 값을 기반으로 다른 값들을 계산합니다.
			int fatUsage = (int)(kcalUsage * 0.65) / 9;
			String uuid = UUID.randomUUID().toString();

			// 6. Workout 엔티티를 생성합니다.
			Workout workout = Workout.of(
				user,
				uuid,
				execTime,
				kcalUsage,
				fatUsage,
				zone2Rate,
				activity
			);

			// 1. 유닉스 초 단위로 날짜 범위의 시작과 끝을 정의합니다.
			long startEpochSecond = LocalDateTime.of(2025, 6, 1, 0, 0).toEpochSecond(ZoneOffset.UTC);
			long endEpochSecond = LocalDateTime.of(2025, 7, 31, 0, 0).toEpochSecond(ZoneOffset.UTC);

			// 2. 해당 범위 내에서 랜덤 long 값을 생성합니다.
			long randomEpochSecond = ThreadLocalRandom.current().nextLong(startEpochSecond, endEpochSecond);

			// workout.setCreatedAt(Instant.ofEpochSecond(randomEpochSecond));
			// 7. 생성된 엔티티를 저장합니다.
			workoutRepository.save(workout);
		}
	}
}
