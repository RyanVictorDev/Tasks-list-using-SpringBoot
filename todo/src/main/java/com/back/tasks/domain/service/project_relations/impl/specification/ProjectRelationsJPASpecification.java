package com.back.tasks.domain.service.project_relations.impl.specification;

import com.back.tasks.domain.entity.project_relations.ProjectRelationsEntity;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class ProjectRelationsJPASpecification {

    public static Specification<ProjectRelationsEntity> withProjectIdEquals(final Long id) {
        return (root, query, cb) -> id == null ? null :
                cb.equal(root.get("project").get("id"), id);
    }

    public static Specification<ProjectRelationsEntity> withUserIdEquals(final Long userId) {
        return (root, query, cb) -> userId == null ? null :
                cb.equal(root.get("user").get("id"), userId);
    }
}
