package com.pragma.crediya.api.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.pragma.crediya.api.DTOs.UserDTO;
import com.pragma.crediya.model.user.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    
    // @Mapping(target = "fecha_nacimiento", source = "fecha_nacimiento")
    UserDTO toResponse(User user);
    List<UserDTO> toResponseList(List<User> users);
    
    // @Mapping(target = "fecha_nacimiento", source = "fecha_nacimiento")
    User toModel(UserDTO userDTO);
}
