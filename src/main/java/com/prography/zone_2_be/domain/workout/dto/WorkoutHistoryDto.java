// com.prography.zone_2_be.domain.workout.dto/WorkoutHistoryDto.java (예시)
package com.prography.zone_2_be.domain.workout.dto;

import com.prography.zone_2_be.domain.workout.entity.Activity; // Activity Enum 타입 임포트
import com.prography.zone_2_be.domain.workout.entity.Workout; // 엔티티 변환용

import lombok.*;

@Getter
@NoArgsConstructor // 기본 생성자
@AllArgsConstructor // 모든 필드를 인자로 받는 생성자
@ToString
@Builder
public class WorkoutHistoryDto { // 이름은 WorkoutHistoryDto나 WorkoutHistoryResponseItem 등으로 변경
    private Long execTime;
    private Integer kcalUsage;
    private Integer fatUsage; // 요청하신 DTO 필드명에 맞춤
    private Activity activity;

    // Workout 엔티티를 WorkoutHistoryDto로 변환하는 팩토리 메서드
    public static WorkoutHistoryDto from(Workout workout) {
        return WorkoutHistoryDto.builder()
                .execTime(workout.getExecTime())
                .kcalUsage(workout.getKcalUsage())
                .fatUsage(workout.getFatUsage())
                .activity(workout.getActivity())
                .build();

    }
}