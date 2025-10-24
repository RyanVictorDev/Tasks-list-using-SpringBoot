package com.back.tasks.domain.repository.user;

import com.back.tasks.domain.entity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity,Integer>, JpaSpecificationExecutor<UserEntity> {
    Optional<UserEntity> findByEmail(String email);

    UserEntity findById(Long id);

    UserEntity findByIdAndDeletedFalse(long id);
}
