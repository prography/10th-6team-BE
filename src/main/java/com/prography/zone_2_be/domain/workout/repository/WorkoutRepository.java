package com.prography.zone_2_be.domain.workout.repository;

import com.prography.zone_2_be.domain.user.entity.User;
import com.prography.zone_2_be.domain.workout.dto.WorkoutGetHistoryResponse;
import com.prography.zone_2_be.domain.workout.dto.WorkoutTotalDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.prography.zone_2_be.domain.workout.entity.Workout;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository()
public interface WorkoutRepository extends JpaRepository<Workout, Long> {
    List<Workout> findByUserAndCreatedAtGreaterThanEqualAndCreatedAtLessThanOrderByCreatedAtDesc(
            User user,
            Long startTime,
            Long endTime,
            Pageable pageable);

    // WorkoutTotalDto의 경로가 바뀌었을 때 쿼리 내의 패키지 경로도 수정해줘야 함
    @Query("SELECT new com.prography.zone_2_be.domain.workout.dto.WorkoutTotalDto(" +
            "SUM(w.execTime), SUM(w.fatUsage), SUM(w.kcalUsage)) " + // w.fatUsage 참조
            "FROM Workout w " +
            "WHERE w.user = :user " +
            "AND w.createdAt >= :startTime AND w.createdAt < :endTime")
    Optional<WorkoutTotalDto> findTotalSumsByUserIdAndCreatedAtRange(
            @Param("user") User user,
            @Param("startTime") Long startTime,
            @Param("endTime") Long endTime);
}
