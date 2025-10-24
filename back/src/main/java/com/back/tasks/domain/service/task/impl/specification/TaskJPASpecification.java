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

    public static Specification<TaskEntity> withTitleLike(final String title) {
        return (root, query, cb) -> {
            if (title == null || title.trim().isEmpty()) return null;
            String pattern = "%" + title.toLowerCase() + "%";
            return cb.like(cb.lower(root.get("title")), pattern);
        };
    }

    public static Specification<TaskEntity> withDescriptionLike(final String description) {
        return (root, query, cb) -> {
            if (description == null || description.trim().isEmpty()) return null;
            String pattern = "%" + description.toLowerCase() + "%";
            return cb.like(cb.lower(root.get("description")), pattern);
        };
    }

    public static Specification<TaskEntity> withResponsibleUserNameLike(final String name) {
        return (root, query, cb) -> {
            if (name == null || name.trim().isEmpty()) return null;
            String pattern = "%" + name.toLowerCase() + "%";
            return cb.like(cb.lower(root.get("responsible").get("name")), pattern);
        };
    }

    public static Specification<TaskEntity> withProjectNameLike(final String name) {
        return (root, query, cb) -> {
            if (name == null || name.trim().isEmpty()) return null;
            String pattern = "%" + name.toLowerCase() + "%";
            return cb.like(cb.lower(root.get("project").get("name")), pattern);
        };
    }

    public static Specification<TaskEntity> withProjectIdEquals(final Long id) {
        return (root, query, cb) -> id == null ? null :
                cb.equal(root.get("project").get("id"), id);
    }

    public static Specification<TaskEntity> withResponsibleUserEmailLike(final String email) {
        return (root, query, cb) -> {
            if (email == null || email.trim().isEmpty()) return null;
            String pattern = "%" + email.toLowerCase() + "%";
            return cb.like(cb.lower(root.get("responsible").get("email")), pattern);
        };
    }
}
