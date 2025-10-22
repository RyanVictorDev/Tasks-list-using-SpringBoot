package com.back.tasks.domain.entity.project;

import com.back.tasks.domain.entity.BaseEntity;
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
@Table(name = "projects")
@Entity
public class ProjectEntity extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    private UserEntity manager;

    @Column(nullable = false)
    private Boolean deleted;
}
