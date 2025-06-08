package com.prography.zone_2_be.domain.user.dto;

import com.prography.zone_2_be.domain.user.entity.Gender;
import com.prography.zone_2_be.domain.user.entity.Role;
import com.prography.zone_2_be.domain.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@AllArgsConstructor
@Getter
public class UserFindResponse {
    private String uuid;
    private String email;
    private long birth;
    private int height;
    private int weight;
    private Gender gender;

    public static UserFindResponse from(User user) {
        return UserFindResponse.builder()
                .uuid(user.getUuid())
                .email(user.getEmail())
                .birth(user.getBirth())
                .height(user.getHeight())
                .weight(user.getWeight())
                .gender(user.getGender())
                .build();
    }
}
