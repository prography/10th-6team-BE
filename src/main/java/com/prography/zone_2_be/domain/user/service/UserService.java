package com.prography.zone_2_be.domain.user.service;

import com.prography.zone_2_be.domain.user.entity.User;
import com.prography.zone_2_be.domain.user.exception.UserNotFoundException;
import com.prography.zone_2_be.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User findUserByOAuth2Key(String oAuth2Key) throws UserNotFoundException {
        User user = userRepository.findByoAuth2Key(oAuth2Key).orElseThrow(UserNotFoundException::new);
        user.setAuthoritiesWithRole();

        return user;
    }
}
