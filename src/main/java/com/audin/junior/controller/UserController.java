package com.audin.junior.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.audin.junior.dto.request.UserDTORequest;
import com.audin.junior.dto.response.UserDTOResponse;
import com.audin.junior.entity.User;
import com.audin.junior.mapper.AuthMapper;
import com.audin.junior.service.UserService;

import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@AllArgsConstructor
@RequestMapping(path = "users")
public class UserController {
    private final UserService userService;
    private final AuthMapper authMapper;
    @PutMapping("/{id}")
    public @ResponseBody UserDTOResponse updateInfo(@PathVariable Integer id, @RequestBody UserDTORequest dto) {
        User user = this.userService.updateInfo(id, dto);
        UserDTOResponse dtoResponse = this.authMapper.toDto(user);

        return ResponseEntity.ok(dtoResponse).getBody();
    }
}
