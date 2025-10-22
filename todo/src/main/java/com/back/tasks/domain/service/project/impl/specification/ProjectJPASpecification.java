package com.back.tasks.domain.service.project.impl.specification;

import com.back.tasks.domain.entity.project.ProjectEntity;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class ProjectJPASpecification {

    public static Specification<ProjectEntity> withManagerIdEquals(String managerId){
        return (root, query, cb) -> managerId == null ? null :
                cb.equal(root.get("manager").get("id"), managerId);
    }

}
