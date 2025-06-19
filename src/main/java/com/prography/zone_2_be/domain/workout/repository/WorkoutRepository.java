package com.prography.zone_2_be.domain.workout.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.prography.zone_2_be.domain.workout.entity.Workout;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository()
public interface WorkoutRepository extends JpaRepository<Workout, Long> {
    List<Workout> findByUserIdAndCreatedAtBetweenOrderByCreatedAtDesc(
            Long userId,
            Long startDate, // created_at >= startDate
            Long endDate,   // created_at <= endDate (주의: Between은 포함)
            Pageable pageable);
}
