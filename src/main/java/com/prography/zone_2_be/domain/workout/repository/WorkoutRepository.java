package com.prography.zone_2_be.domain.workout.repository;

import com.prography.zone_2_be.domain.user.entity.User;
import com.prography.zone_2_be.domain.workout.dto.IWorkoutTotalDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.prography.zone_2_be.domain.workout.entity.Workout;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository()
public interface WorkoutRepository extends JpaRepository<Workout, Long> {
    @Query("SELECT w FROM Workout w " +
            "WHERE w.user = :user " +
            "AND w.createdAt >= :startTime AND w.createdAt < :endTime " +// <- w.createdAt은 Instant 타입
            "ORDER BY w.createdAt desc")
    List<Workout> findWorkoutsByUserIdAndCreatedAtRange(
            @Param("user") User user,
            @Param("startTime") Instant startTime, // <- 초 단위 Long을 받을 예정
            @Param("endTime") Instant endTime,     // <- 초 단위 Long을 받을 예정
            Pageable pageable);

    // WorkoutTotalDto의 경로가 바뀌었을 때 쿼리 내의 패키지 경로도 수정해줘야 함
    @Query("SELECT SUM(w.execTime) as execTimeSum, " + // <- DTO 인터페이스의 getter 이름과 일치하도록 AS 사용
            "SUM(w.fatUsage) as fatUsageSum, " +     // <- DTO 인터페이스의 getter 이름과 일치하도록 AS 사용
            "SUM(w.kcalUsage) as kcalUsageSum " +    // <- DTO 인터페이스의 getter 이름과 일치하도록 AS 사용
            "FROM Workout w " +
            "WHERE w.user = :user " +
            "AND w.createdAt >= :startTime AND w.createdAt < :endTime")
    Optional<IWorkoutTotalDto> findTotalSumsByUserIdAndCreatedAtRange(
            @Param("user") User user,
            @Param("startTime") Instant startTime,
            @Param("endTime") Instant endTime);
}
