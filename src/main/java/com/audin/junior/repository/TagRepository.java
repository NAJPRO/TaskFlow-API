package com.audin.junior.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.audin.junior.entity.Tag;
import com.audin.junior.entity.User;

@Repository
public interface TagRepository extends JpaRepository<Tag, Integer>{
    List<Tag> findByUser(User user);

    List<Tag> findAllByIdInAndUser(List<Integer> ids, User user);
    List<Tag> findByUserOrderByCreatedAtDesc(User user);

    @Query("FROM Tag t WHERE t.user = :user AND t.name = :name")
    Optional<Tag> findByNameForUser(User user, String name);


}
