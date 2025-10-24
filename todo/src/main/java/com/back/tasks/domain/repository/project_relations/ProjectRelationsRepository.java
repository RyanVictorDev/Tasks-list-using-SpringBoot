package com.back.tasks.domain.repository.project_relations;

import com.back.tasks.domain.entity.project_relations.ProjectRelationsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProjectRelationsRepository extends JpaRepository<ProjectRelationsEntity, Long>, JpaSpecificationExecutor<ProjectRelationsEntity> {
}