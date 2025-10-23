package com.back.tasks.domain.service.project.impl.specification;

import com.back.tasks.domain.entity.project.ProjectEntity;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class ProjectJPASpecification {

    public static Specification<ProjectEntity> withManagerIdEquals(Long managerId){
        return (root, query, cb) -> managerId == null ? null :
                cb.equal(root.get("manager").get("id"), managerId);
    }

    public static Specification<ProjectEntity> withProjectIdEquals(Long projectId){
        return (root, query, cb) -> projectId == null ? null :
                cb.equal(root.get("id"), projectId);
    }

    public static Specification<ProjectEntity> withProjectNameEquals(String projectName){
        return (root, query, cb) -> projectName == null || projectName.isEmpty() ? null :
                cb.equal(root.get("name"), projectName);
    }

    public static Specification<ProjectEntity> withProjectDescriptionEquals(String projectDescription){
        return (root, query, cb) -> projectDescription == null || projectDescription.isEmpty() ? null :
                cb.equal(root.get("description"), projectDescription);
    }
}
