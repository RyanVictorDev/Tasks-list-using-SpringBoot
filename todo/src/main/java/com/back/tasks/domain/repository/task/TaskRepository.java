package com.back.tasks.domain.repository.task;

import com.back.tasks.domain.entity.task.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface TaskRepository extends JpaRepository<TaskEntity, Integer>, JpaSpecificationExecutor<TaskEntity> {
    Optional<TaskEntity> findById(Long id);
    Optional<TaskEntity> findByIdAndDeletedFalse(Long id);
}
