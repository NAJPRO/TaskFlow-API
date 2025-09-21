package com.audin.junior.mapper;

import com.audin.junior.dto.request.RegisterDTORequest;
import com.audin.junior.entity.User;

public interface AuthMapper {
    public User toEntity(RegisterDTORequest dto);
}
