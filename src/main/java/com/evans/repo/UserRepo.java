package com.evans.repo;

import com.evans.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<UserEntity,Long> {
    UserEntity findByUsername(String username);
    UserEntity findByEmail(String email);
    Optional<UserEntity> findByResetToken(String resetToken);
}
