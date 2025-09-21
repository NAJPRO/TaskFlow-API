package com.audin.junior.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.audin.junior.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByEmail(String email);
    boolean existsByPseudo(String pseudo);
    Optional<User> findByEmail(String email);
    Optional<User> findByPseudo(String pseudo);

}
