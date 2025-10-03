package com.audin.junior.service.impl;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.audin.junior.dto.request.TagDTORequest;
import com.audin.junior.dto.response.TagDTOResponse;
import com.audin.junior.entity.Tag;
import com.audin.junior.entity.User;
import com.audin.junior.mapper.TagMapper;
import com.audin.junior.repository.TagRepository;
import com.audin.junior.service.TagService;
import com.audin.junior.utils.AuthUtils;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Service
@AllArgsConstructor
@Getter
@Setter
public class TagServiceImpl implements TagService{

    private final TagRepository tagRepository;
    private final TagMapper tagMapper;
    private final AuthUtils authUtils;

    @Override
    public List<TagDTOResponse> getAllTags(){
        User user = authUtils.getCurrentUser();

        List<Tag> tags = this.tagRepository.findByUserOrderByCreatedAtDesc(user);
        return tagMapper.toDto(tags);
    }

    @Override
    public Tag save(TagDTORequest dto){
        User user = authUtils.getCurrentUser();
     
        Tag tag = this.tagMapper.toEntity(dto);
        return this.tagRepository.findByNameForUser(user, tag.getName()).orElseGet(() -> {
            tag.setUser(user);
            this.tagRepository.save(tag);
            return tag;
        });
    }

    @Override
    public void delete(Integer id){
        User user = authUtils.getCurrentUser();
        Tag tag = this.tagRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Le tag avec l'id " + id + " n'existe pas."));
        if (!tag.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("Vous n'êtes pas autorisé à supprimer ce tag.");
        }
        this.tagRepository.delete(tag);
    }

    @Override
    public List<Tag> findAllByIdsAndUser(List<Integer> ids){
        User user = authUtils.getCurrentUser();
        return this.tagRepository.findAllByIdInAndUser(ids, user);
    }

    @Override
    public List<Tag> findAllByUser(){
        User user = authUtils.getCurrentUser();
        return this.tagRepository.findByUser(user);
    }

}
