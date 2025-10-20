package com.back.tasks.domain.service.task.impl.specification;

import com.back.tasks.domain.entity.task.TaskEntity;
import com.back.tasks.domain.io.enums.TaskStatus;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@NoArgsConstructor
public class TaskJPASpecification {
    public static Specification<TaskEntity> withResponsibleUserId(final Long id) {
        return (root, query, cb) -> id == null ? null :
                cb.equal(root.get("responsible").get("id"), id);
    }

    public static Specification<TaskEntity> withDeleted(final Boolean deleted) {
        return (root, query, cb) ->  deleted == null ? null :
                cb.equal(root.get("deleted"), deleted);
    }

    public static Specification<TaskEntity> withStatus(final TaskStatus status) {
        return (root, query, cb) -> status == null ? null :
                cb.equal(root.get("status"), status);
    }
}
