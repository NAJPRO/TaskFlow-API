package com.audin.junior.mapper;

import java.util.List;

import com.audin.junior.dto.request.TagDTORequest;
import com.audin.junior.dto.response.TagDTOResponse;
import com.audin.junior.entity.Tag;

public interface TagMapper {
    Tag toEntity(TagDTORequest dto);
    TagDTOResponse toDto(Tag entity);
    List<TagDTOResponse> toDto(List<Tag> entities);
}
