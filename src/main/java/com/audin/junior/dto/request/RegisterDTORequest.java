package com.audin.junior.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class RegisterDTORequest {
    private String pseudo;
    private String email;
    private String password;
    private String confirmPassword;
}
