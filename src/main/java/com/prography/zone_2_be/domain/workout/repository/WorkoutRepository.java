package com.prography.zone_2_be.domain.workout.repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.prography.zone_2_be.domain.user.entity.User;
import com.prography.zone_2_be.domain.workout.dto.IWorkoutTotalDto;
import com.prography.zone_2_be.domain.workout.entity.Workout;

@Repository()
public interface WorkoutRepository extends JpaRepository<Workout, Long> {
	@Query("SELECT w FROM Workout w " +
		"WHERE w.user = :user " +
		"AND w.createdAt >= :startTime AND w.createdAt < :endTime " +
		"ORDER BY w.createdAt desc")
	List<Workout> findWorkoutsByUserIdAndCreatedAtRange(
		@Param("user") User user,
		@Param("startTime") Instant startTime,
		@Param("endTime") Instant endTime,
		Pageable pageable);

	// WorkoutTotalDto의 경로가 바뀌었을 때 쿼리 내의 패키지 경로도 수정해줘야 함
	@Query("SELECT SUM(w.execTime) as execTimeSum, " +
		"SUM(w.fatUsage) as fatUsageSum, " +
		"SUM(w.kcalUsage) as kcalUsageSum " +
		"FROM Workout w " +
		"WHERE w.user = :user " +
		"AND w.createdAt >= :startTime AND w.createdAt < :endTime")
	Optional<IWorkoutTotalDto> findTotalSumsByUserIdAndCreatedAtRange(
		@Param("user") User user,
		@Param("startTime") Instant startTime,
		@Param("endTime") Instant endTime);

	Optional<Workout> findByUuid(String uuid);
}
