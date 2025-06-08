package com.prography.zone_2_be.domain.user.service;

import com.prography.zone_2_be.domain.user.dto.UserFindResponse;
import com.prography.zone_2_be.domain.user.dto.UserUpdateRequest;
import com.prography.zone_2_be.domain.user.entity.User;
import com.prography.zone_2_be.domain.user.exception.UserNotFoundException;
import com.prography.zone_2_be.domain.user.repository.UserRepository;
import com.prography.zone_2_be.global.utils.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public User findUserByUuid(String uuid) throws UserNotFoundException {
        return userRepository.findByUuid(uuid).orElseThrow(UserNotFoundException::new);
    }


    public UserFindResponse findUser(){
        return UserFindResponse.from(userRepository.findByUuid(JwtUtil.getUuid()).orElseThrow(UserNotFoundException::new));
    }

    @Transactional
    public void updateUser(UserUpdateRequest dto){
        String uuid = JwtUtil.getUuid();
        User user = userRepository.findByUuid(uuid).orElseThrow(UserNotFoundException::new);

        user.setWeight(dto.weight);
        user.setHeight(dto.height);
        user.setBirth(dto.birth);
        user.setGender(dto.gender);
    }
}
