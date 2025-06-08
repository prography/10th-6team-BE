package com.prography.zone_2_be.domain.user.controller;

import com.prography.zone_2_be.domain.user.dto.UserFindResponse;
import com.prography.zone_2_be.domain.user.dto.UserUpdateRequest;
import com.prography.zone_2_be.domain.user.service.UserService;
import com.prography.zone_2_be.global.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
@Slf4j
public class UserController {
    private final UserService userService;

    @PostMapping("")
    public ResponseEntity<ApiResponse<Void>> updateUser(@Valid @RequestBody UserUpdateRequest request) {
        userService.updateUser(request);
        return ApiResponse.success();
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse<UserFindResponse>> findUser() {
        return ApiResponse.success(userService.findUser());
    }
}
