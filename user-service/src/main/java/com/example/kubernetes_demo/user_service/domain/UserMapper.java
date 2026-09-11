package com.example.kubernetes_demo.user_service.domain;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import com.example.kubernetes_demo.user_service.data.User;
import com.example.kubernetes_demo.user_service.domain.dto.UserDto;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    
    UserDto toDto(User user);
}
