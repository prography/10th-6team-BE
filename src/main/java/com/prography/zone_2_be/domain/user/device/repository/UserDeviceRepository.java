package com.prography.zone_2_be.domain.user.device.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.prography.zone_2_be.domain.user.device.entity.UserDevice;

@Repository
public interface UserDeviceRepository extends JpaRepository<UserDevice, Long> {

}
