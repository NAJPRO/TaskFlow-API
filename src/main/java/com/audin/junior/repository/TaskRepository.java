package com.audin.junior.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.audin.junior.entity.Task;
import com.audin.junior.entity.User;
import com.audin.junior.enums.TaskStatus;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer>{
    Optional<Task> findByIdAndUser(Integer id, User user);
    Optional<Task> findBySlugAndUser(String slug, User user);
    List<Task> findAllByUser(User user);
    List<Task> findAllByUserAndStatus(User user, TaskStatus status);
}
