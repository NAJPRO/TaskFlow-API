package com.audin.junior.service.Impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.audin.junior.entity.User;
import com.audin.junior.repository.UserRepository;
import com.audin.junior.service.UserService;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Service
@Getter
@Setter
@AllArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        return this.userRepository.findByEmail(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public User findByEmail(String email) {
        return this.userRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public User findByPseudo(String pseudo) {
        return this.userRepository.findByPseudo(pseudo)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

}
