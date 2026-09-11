package com.example.kubernetes_demo.user_service.api;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import com.example.kubernetes_demo.user_service.api.dto.CreateUserResponse;
import com.example.kubernetes_demo.user_service.domain.dto.UserDto;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserApiResponseMapper {
    
    CreateUserResponse toCreateUserResponse(UserDto user);
}
