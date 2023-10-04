package com.agh.mallet.infrastructure.mapper;

import com.agh.mallet.api.UserInformationDTO;
import com.agh.mallet.domain.user.entity.UserJPAEntity;

public class UserInformationDTOMapper {

    private UserInformationDTOMapper() {
    }

    public static UserInformationDTO from(UserJPAEntity user) {
        return UserInformationDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .registrationDate(user.getRegistrationDate())
                .email(user.getEmail())
                .build();
    }
}
