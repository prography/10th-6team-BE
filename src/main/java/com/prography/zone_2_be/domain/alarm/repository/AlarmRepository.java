package com.prography.zone_2_be.domain.alarm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.prography.zone_2_be.domain.alarm.entity.Alarm;

@Repository
public interface AlarmRepository extends JpaRepository<Alarm, Long> {

}
