package com.audin.junior.mapper.Impl;

import org.springframework.stereotype.Component;

import com.audin.junior.dto.request.RegisterDTORequest;
import com.audin.junior.dto.response.UserDTOResponse;
import com.audin.junior.entity.User;
import com.audin.junior.mapper.AuthMapper;

@Component
public class AuthMapperImpl implements AuthMapper {

    @Override
    public User toEntity(RegisterDTORequest dto) {
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setPseudo(dto.getPseudo());
        user.setPassword(dto.getPassword());
        return user;
    }

    @Override
    public UserDTOResponse toDto(User entity, String accessToken) {
        UserDTOResponse userDTO = new UserDTOResponse(
            entity.getId(),
            entity.getPseudo(),
            entity.getEmail(),
            entity.getName(),
            entity.getEmailVerifiedAt(),
            accessToken
        );
        return userDTO;
    }

}
