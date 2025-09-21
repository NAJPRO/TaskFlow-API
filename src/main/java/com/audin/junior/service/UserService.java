package com.audin.junior.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.audin.junior.entity.User;

public interface UserService extends UserDetailsService {
    public User findByEmail(String email);

    public User findByPseudo(String pseudo);
}
