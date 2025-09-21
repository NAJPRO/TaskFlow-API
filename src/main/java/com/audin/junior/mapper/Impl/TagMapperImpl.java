package com.audin.junior.mapper.Impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.audin.junior.dto.request.TagDTORequest;
import com.audin.junior.dto.response.TagDTOResponse;
import com.audin.junior.entity.Tag;
import com.audin.junior.mapper.TagMapper;

@Component
public class TagMapperImpl implements TagMapper{

    @Override
    public TagDTOResponse toDto(Tag entity) {
        TagDTOResponse tag = new TagDTOResponse(
            entity.getId(),
            entity.getUser().getId(),
            entity.getName(),
            entity.getCreatedAt()
        );
        return tag;
    }

    @Override
    public List<TagDTOResponse> toDto(List<Tag> entities) {
        List<TagDTOResponse> tagsResponses = new ArrayList<TagDTOResponse>();
        for (Tag tag : entities) {
            tagsResponses.add(this.toDto(tag));
        }
        return tagsResponses;
    }

    @Override
    public Tag toEntity(TagDTORequest dto) {
        Tag tag = new Tag();
        tag.setName(dto.name());
        return tag;
    }

}
