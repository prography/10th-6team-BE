package com.prography.zone_2_be.domain.alarm.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.prography.zone_2_be.domain.alarm.entity.Alarm;
import com.prography.zone_2_be.domain.alarm.entity.AlarmType;
import com.prography.zone_2_be.domain.user.entity.User;

@Repository
public interface AlarmRepository extends JpaRepository<Alarm, Long> {

	Optional<Alarm> findByUserAndAlarmType(User user, AlarmType alarmType);

	List<Alarm> findAllByUser(User user);
}
