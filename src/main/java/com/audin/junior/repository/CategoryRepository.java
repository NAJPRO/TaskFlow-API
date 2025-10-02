package com.audin.junior.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.audin.junior.entity.Category;
import com.audin.junior.entity.User;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Optional<Category> findByIdAndUser(Integer id, User user);

    List<Category> findAllByUser(User user);
    Optional<Category> findByNameAndUser(String name, User user);

}
