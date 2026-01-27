package com.prography.zone_2_be.domain.user.repository;

import java.time.Instant;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.prography.zone_2_be.domain.user.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByOauth2Key(String Oauth2Key);

	Optional<User> findByUuid(String uuid);

	boolean existsByOauth2Key(String oAuth2Key);

	long countByCreatedAtBetween(Instant start, Instant end);
}
