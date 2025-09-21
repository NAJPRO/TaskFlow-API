package com.audin.junior.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.audin.junior.dto.request.TagDTORequest;
import com.audin.junior.dto.response.TagDTOResponse;
import com.audin.junior.service.TagService;

import lombok.AllArgsConstructor;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;




@RestController
@AllArgsConstructor
@RequestMapping(path = "tags")
public class TagController {

    private final TagService tagService;
    @GetMapping
    public @ResponseBody List<TagDTOResponse> getAllTags() {
        return tagService.getAllTags();
    }

    @PostMapping
    public @ResponseBody TagDTOResponse save(@RequestBody TagDTORequest dtoRequest) {
        return tagService.save(dtoRequest);
    }
    
}
