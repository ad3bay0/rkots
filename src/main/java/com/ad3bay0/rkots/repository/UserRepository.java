package com.ad3bay0.rkots.repository;

import java.util.Optional;
import java.util.UUID;

import com.ad3bay0.rkots.models.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,UUID> {

    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Optional<User> findByUsernameOrEmail(String username, String email);
    
}