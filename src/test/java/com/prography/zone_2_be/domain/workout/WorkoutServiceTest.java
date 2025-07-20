package com.prography.zone_2_be.domain.workout;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.prography.zone_2_be.domain.user.entity.User;
import com.prography.zone_2_be.domain.user.repository.UserRepository;
import com.prography.zone_2_be.domain.workout.entity.Activity;
import com.prography.zone_2_be.domain.workout.entity.Workout;
import com.prography.zone_2_be.domain.workout.repository.WorkoutRepository;
import com.prography.zone_2_be.global.utils.JwtUtil;

@SpringBootTest
@ActiveProfiles("test")
public class WorkoutServiceTest {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private WorkoutRepository workoutRepository;

	@Autowired
	private JwtUtil jwtUtil;

	@Test
	public void createDummyDataWithAuthKey() {
		// 1. 기준이 될 사용자를 한 번 조회합니다.
		String oauth2Key = "key";

		createDummyData(oauth2Key);
	}

	@Test
	public void createDummyDataWithJWT() {
		String token = "eyJhbGciOiJIUzM4NCJ9.eyJqdGkiOiIxYTQ5OThhOS03YjczLTRhOTItYjkyOC1kZmZiMTMyYWQ5MjUiLCJzdWIiOiJrZXkiLCJpYXQiOjE3NTI5OTUyNzQsImV4cCI6MTc1Mjk5ODg3NH0.7-696ZgV6x_Dr3MVlmDL1398x11XaxHimf3RiPQNp5nYKuHXvBHKQNKbU3aSrzEn";

		createDummyData(jwtUtil.getOAuth2Key(token));

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

			// 7. 생성된 엔티티를 저장합니다.
			workoutRepository.save(workout);
		}
	}
}
