package com.audin.junior.service;

import java.util.List;

import com.audin.junior.dto.request.TagDTORequest;
import com.audin.junior.dto.response.TagDTOResponse;
import com.audin.junior.entity.Tag;

public interface TagService {
    public List<TagDTOResponse> getAllTags();

    public TagDTOResponse save(TagDTORequest dto);

    public void delete(Integer id);

    public List<Tag> findAllByIdsAndUser(List<Integer> ids);

    public List<Tag> findAllByUser();
}
