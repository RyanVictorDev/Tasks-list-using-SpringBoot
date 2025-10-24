package com.back.tasks.domain.entity.project_relations;

import com.back.tasks.domain.entity.BaseEntity;
import com.back.tasks.domain.entity.project.ProjectEntity;
import com.back.tasks.domain.entity.user.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "project_relations")
@Entity
public class ProjectRelationsEntity extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private ProjectEntity project;

    @Column(nullable = false)
    private Boolean deleted;
}
