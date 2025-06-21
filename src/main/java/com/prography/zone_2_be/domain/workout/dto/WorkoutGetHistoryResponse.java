// com.prography.zone_2_be.domain.workout.dto/WorkoutGetHistoryResponse.java
package com.prography.zone_2_be.domain.workout.dto;

import lombok.Getter;
import lombok.NoArgsConstructor; // Lombok의 @RequiredArgsConstructor 대신 AllArgsConstructor를 쓰거나 NoArgsConstructor 추가
import lombok.AllArgsConstructor; // 모든 필드를 인자로 받는 생성자

import java.util.List;

@Getter
@NoArgsConstructor // Lombok 어노테이션 사용 시 필요
@AllArgsConstructor // Lombok 어노테이션 사용 시 필요
public class WorkoutGetHistoryResponse {
    // WorkoutTotalSumsDto에서 가져올 필드들
    private Long totalExecTime;
    private Long totalKcalUsage;
    private Long totalZone2FatUsage; // DTO 필드명은 유지

    // 개별 운동 기록 DTO 리스트
    private List<WorkoutHistoryDto> histories; // 분리된 DTO 사용
}